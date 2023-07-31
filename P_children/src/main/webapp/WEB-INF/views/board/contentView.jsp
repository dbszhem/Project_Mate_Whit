
<!-- board/contentView.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<%
   String num2 = request.getParameter("num");
   if(num2.equals("null")){
      num2 = "1";
   }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mate With 게시판 글보기</title>
<script src="https://code.jquery.com/jquery-3.6.4.js" integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E=" crossorigin="anonymous"></script>
<link href="${pageContext.request.contextPath}/resources/chenggyu/board.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
   // 본문 관련 기능 시작 -------------------------------------------------------------------------------------------------------

   // 수정, 삽입 등 변환 발생시 임시로 데이터 저장해두는 장소
   let htmlStorage = "";
   let storagePosition = "";
   let htmlStorageRecomment = "";
   let storagePositionRecomment = "";
   
   // 파일 업로드 시 img 태그에 그림 화면 보이기
   function readURL(input){
      var file = input.files[0]; // 파일 정보
      if(file != ""){
         var reader = new FileReader();
         reader.readAsDataURL(file); // 파일 정보 읽어오기
         reader.onload = function(e){ // 읽기에 성공하면 결과 표시
            $("#preview").attr("src", e.target.result)
         }
         
      }
   }

   
   // 로그인 요구 기능
   function loginPlease() {
      alert("로그인 후 사용할 수 있는 기능입니다.");
   }
   
   // 본문 관련 기능 끝 -------------------------------------------------------------------------------------------------------
   // 댓글 관련 기능 시작 -------------------------------------------------------------------------------------------------------
   
   // 댓글 추가
   function rep() {
      if($("#frm #content").val() == "") { // 내용이 없을 때
         alert("댓글 내용이 없습니다!");
      } else {
         let form = {}
         let arr = $("#frm").serializeArray();
         for( i = 0; i <arr.length ; i++) {
            form[arr[i].name] = arr[i].value
         }
         // console.log(form);
         
         $.ajax({
            url: "addReply",
            type: "POST", 
            data: JSON.stringify(form),
            contentType: "application/json; charset=utf-8",
            success: function(data) {
               if(data == 1)
                  alert("댓글 추가 성공~!")
                  $("#frm #content").val(' ');
                  replyData();
            },
            error: function() {
               alert("Error !!")
            }
            
            
         })
      }
      
   } // rep() end
   
   // 댓글 리스트업 기능(댓글 보이게 하는 기능)
   function replyData() {
	   $("div.rep").show();
      $.ajax({
         url: "replyData/"+$("#write_no").val(), //val() = value
         type: "get",
         dataType: "json",
         success: function(rep) {
            let htm = ""
            let count = 0;  // 해당 게시판의 모든 댓글과 대댓글 수
            if(rep.length > 0) {
               rep.forEach(function(redata){
                  let date = new Date(redata.write_date)
                  let writeDate = date.getFullYear()+"년 " +(date.getMonth() + 1) + "월 "
                  writeDate += date.getDate() + "일 " + date.getHours() + " 시 "
                  writeDate += date.getMinutes() + "분 " + date.getSeconds() + "초"
                  count += 1;
                  
                  if(redata.depth == 0){ // 댓글인 경우 : depth == 0
                     htm += "<div class='reply' align='left' id='rep" + redata.reply_no + "'><div class='reply_innerDiv'>"
                     htm += "<img class='unknownImg' src='https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Unknown_person.jpg/542px-Unknown_person.jpg?20200423155822'>&nbsp;<b>"
                     htm += redata.id + " </b>님 ";
                     htm += "<span style='float:right; font-size:10px;' align='right'>" + writeDate + "</span><br>"
                     htm += "<span class='repContent'>" + redata.content + "</span><hr>"
                     htm += "<input type='hidden' class='replyNo' value='" + redata.reply_no + "'/>"
                     if('${loginUser}' != '') {
                         htm += "<button id='recom' onclick='ShowAddReCommentForm("+ redata.reply_no+")'><b>답글</b></button>"
                      }
                     htm += "<a onclick='reComment("+ redata.reply_no +")'><b>더보기</b></a> "
                     
                     // 댓글 작성자와 현재 유저가 일치하는 경우
                     if(redata.id == $("#user").val() || $("#userGrade").val() == $("#admin").val()) {
                        htm += "<a class='deleteUpdateButton' onclick='confirmDeleteReply(" + redata.reply_no + ","+ redata.write_group +")' >삭제</a>&nbsp;"
                              
                        if(redata.id == $("#user").val()){
                           htm += "<a class='deleteUpdateButton' onclick='updateReply("+ redata.reply_no +")'>수정</a>"
                        }
                              
                        htm += "</div></div>"
                     } else {
                        htm += "</div></div>"
                     }
                  } 
                  
                  
            })
            } // if(rep.list.length > 0) end
            else { // 댓글이 없는 경우
                htm += "<div class='no_reply' align='center'>";
                htm += "<h5>등록된 댓글이 없습니다</h5>";
                htm += "</div>";
            }
            
            $("#reply").html(htm)

         } // success end
         ,
         error: function() {
            alert("댓글 가져오기 실패 !!")
         }
      })
      
   }
   
   // 댓글 삭제 confirm
   function confirmDeleteReply(reply_no, write_group) {
      
      if(!confirm('댓글을 삭제하시겠습니까?')) {
         return false;
      } else {
         location.href = "${contextPath }/board/deleteReply?reply_no=" + reply_no 
                     + "&write_group=" + write_group
                     + "&num=" + <%=num2%>;
      }
   }
   
   // 댓글 수정하기 form 생성
   function updateReply(reply_no){
	   
	   $("div.rep").addClass("hidden");
      
      
      // 다른 댓글 수정창 켜져있을 경우 종료
      if($('#updateContent').val() != null){
    	 cancelUpdateReply();
         //replyData();
      }
      
      // 대댓글 수정 창 켜져있을 경우 종료
      if($('#updateReCommentContent').val() != null){
    	  cancleUpdateReComment();
      }
      
      // 대댓글 입력 창 켜져있을 경우 종료
      if($(".ShowAddReComment").length > 0) {
          cancelAddReComment();
      } 
      
      
      let replyView = ""
      replyView += "<div align='left' id='updateContentDiv"+reply_no +"'><b>"+ $("#user").val() +"</b>"
      replyView += "<form  id='updateResultFrm' action='${contextPath }/board/updateReply?num=<%=num2%>'><input type='hidden' name='id' value='" + $("#user").val() + "' readonly><br>";
      replyView += "<input type='hidden'  name='write_no'  value='" + $('#write_no').val() + "'>"
      replyView += "<input type='hidden' id='updateReply_no' name='updateReply_no' value='" + reply_no + "'>"
      replyView += "<textarea id='updateContent' name='updateContent' rows='5' cols='30' autofocus>" + $('#rep' + reply_no).children('.reply_innerDiv').children('.repContent').text() + "</textarea><br>"
      replyView += "<div class='replyView'>"
      replyView += "<input type='button' onclick='updateReplyConfirm()' value='수정'>"
      replyView += "<input type='button' onclick='cancelUpdateReply()' value='취소'></form><br>"
      replyView += "</div></div>"
      
      // 수정으로 대체될 영역 정보 저장
      htmlStorage = $('#rep'+ reply_no).html();
      storagePosition = reply_no;
      $('#rep'+ reply_no).replaceWith(replyView);
      $('#rep'+ reply_no).children('#updateContent').focus();
      $('div.rep').hide();
   }
   
   // 댓글 수정 확인
   function updateReplyConfirm(){
      if(!confirm('수정하시겠습니까?')){
         return false;
      } else {
        $("div.rep").show();
         $("#updateResultFrm").submit();
      }
   }
   // 댓글 수정 취소
   function cancelUpdateReply(){
	   $('#updateContentDiv'+ storagePosition).replaceWith("<div class='reply' align='left' id='rep"+ storagePosition +"'>"+htmlStorage+"</div>");
	   $("div.rep").show();
   }
   
   // 댓글 관련 기능 끝 -------------------------------------------------------------------------------------------------------
   // 대댓글 관련 기능 시작 -------------------------------------------------------------------------------------------------------
   
   // 대댓글(답글) 보기
   function reComment(reply_no){ // 댓글 reply_no(cGroup)에 해당하는 select 문, 자기 reply_no 순서대로 부르면 된다.
      
      // 대댓글 수정창이 켜져 있을 떄
      if($('#updateReCommentContent').val() != null) {
         $("div > id[value~='updateReComment']").replaceWith(""); // 대댓글 수정 div 삭제
      }
      
      if($("#rep" + reply_no).children(".reComment").length > 0) { // 대댓글(답글)이 나와 있을 경우
         $("#rep" + reply_no).children(".reComment").remove(); // 대댓글 삭제
      } else {
         $.ajax({   
            url: "reCommentData/" + reply_no,
            type: "POST", 
            dataType: "json",   
            contentType: "application/json; charset=utf-8",
            success: function(reComment) {
               let htm = ""
               let count = 0;  // 해당 댓글의 대댓글 수
               if(reComment.length > 0) {
                  reComment.forEach(function(redata){
                     let date = new Date(redata.write_date)
                     let writeDate = date.getFullYear()+"년 " +(date.getMonth() + 1) + "월 "
                     writeDate += date.getDate() + "일 " + date.getHours() + " 시 "
                     writeDate += date.getMinutes() + "분 " + date.getSeconds() + "초"
                     count += 1;
                     
                     if(redata.depth == 1){ // 답글인 경우 : depth == 1
                        htm += "<div class='reComment' align='left' id='reComment" + redata.reply_no + "'> "
                        htm += "<b><img class='unknownImg' src='https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Unknown_person.jpg/542px-Unknown_person.jpg?20200423155822'>&nbsp;" + redata.id + " 님 </b> ";
                        htm += "<span style='float:right; font-size:10px;' align='right'>" + writeDate + "</span><br>"
                        htm += "<span class='repContent'>" + redata.content + "</span><br>"
                        htm += "<input type='hidden' class='replyNo' value='" + redata.reply_no + "'/>"
                        
                        // 대댓글 작성자와 현재 유저가 일치하는 경우 or 관리자인 경우
                        if(redata.id == $("#user").val() || $("#userGrade").val() == $("#admin").val()) {
                           htm += "<a class='deleteUpdateButton' onclick='confirmDeleteReComment(" + redata.reply_no + ","+ redata.write_group +")' >삭제</a>&nbsp;"
                                 
                                 // 대댓글 작성자와 현재 유저가 일치하는 경우
                           if(redata.id == $("#user").val()) {
                              htm += "<a class='deleteUpdateButton' onclick='updateReComment("+ redata.reply_no +")'>수정</a>"
                           }
                           
                           htm += "</div>"
                        } else {
                           htm += "</div>"
                        }
                     } 
                     
                     
               })
               } // if(rep.list.length > 0) end
               else { // 댓글이 없는 경우
                   htm += "<div class='reComment' align='center'>";
                   htm += "<h5>등록된 답글이 없습니다</h5>";
                   htm += "</div>";
               }
               
               $("#rep" + reply_no).append(htm)
            }, //success end
            error: function() {
               alert("Error !!")
            }
         })
      }
      
   
      
   } // reComment end
   
   // 대댓글 삭제 confirm
   function confirmDeleteReComment(reply_no, write_group) {
      if(!confirm("대댓글을 정말로 삭제하시겠습니까?")){
         return false;
      } else {
         location.href = "${contextPath }/board/deleteReply?reply_no=" + reply_no 
         + "&write_group=" + write_group
         + "&num=" + <%=num2%>;
      }
   }
   
   // 대댓글 삽입 폼
   function ShowAddReCommentForm(reply_no){ // 답글 삽입 폼 생성
	   
	  // 댓글 삽입 폼 숨기기
	  $("div.rep").hide();
   
      // 댓글 수정창이 켜져있을 경우 종료
      if($('#updateContent').val() != null){
    	  cancelUpdateReply(); 
      }
      
      // 대댓글 수정 창 켜져있을 경우 종료
      if($('#updateReCommentContent').val() != null){
          $('.updateReComment').remove();
      }
      
      // 다른 대댓글 창이 켜져 있을 경우 종료
      if($(".ShowAddReComment").length > 0 && $("#addReComment"+reply_no).length == 0) {
    	  //alert($("#addReComment"+reply_no).length + "입니다 그리고 .ShowAdd는" + $(".ShowAddReComment").length);
          cancelAddReComment();
      } 
      
      if($("#addReComment"+reply_no).length > 0) {
    	  cancelAddReComment();
      } else {
	      let htm = "";
	      
	      htm += "<div class='ShowAddReComment' id='addReComment"+ reply_no+"'>";
	      htm += "<form method='post' id='addReCommentFrm' action='#'><b> "+$("#user").val()+"</b>";
	      htm += "<input type='hidden' id='cGroup' name='cGroup' value='"+ reply_no +"'>";          // cGroup
	      htm += "<input type='hidden'  name='write_no'  value='" + $('#write_no').val() + "'>"      // write_Group
	      htm += "</b><input type='hidden' name='id' value='" + $("#user").val() + "' readonly><br>"; // id
	      htm += "<textarea name='reCommentContent'></textarea>"
	      htm += "<div class='recom'>"
	      htm += "<input type='button' onclick='addReComment("+ reply_no +")' value='답글'>  &nbsp; "
	      htm += "</form><button onclick='cancelAddReComment()'> 취소 </button></div>"
	      htm += "</div>"
	      
	      //htmlStorageRecomment = $('#reComment'+ reply_no).html();
	      //storagePositionRecomment = reply_no;
	      
	      $("#rep" + reply_no).children(".reply_innerDiv").append(htm);
	      $("div.rep").hide();
	      //alert($("#addReComment"+reply_no).length + "입니다 그리고 .ShowAdd는" + $(".ShowAddReComment").length);
      }
   } // ShowAddReCommentForm end
   
   // 대댓글 생성 화면 지우기
   function cancelAddReComment(){
      $("div.rep").show();
      $(".ShowAddReComment").remove();
   }
   
   
   // 대댓글 생성
   function addReComment(cGroup) {
      let form = {}
      let arr = $("#addReCommentFrm").serializeArray();
      for( i = 0; i <arr.length ; i++) {
         form[arr[i].name] = arr[i].value
      }
            
      if(arr[3].value != "") {
         $.ajax({
            url: "addReComment",
            type: "POST", 
            data: JSON.stringify(form),
            contentType: "application/json; charset=utf-8",
            success: function(data) {
               if(data == 1)
                  alert("댓글 추가 성공")
                  cancelAddReComment();     // 추가 성공 후 답글 입력창 종료
                  reComment(cGroup);
            },
            error: function() {
            }
            
         })
      } else {
         alert("입력값이 없습니다");
      }
      
   } // rep() end
   
   // 대댓글 수정하기
   function updateReComment(reply_no){
      
      // 댓글 수정 창 켜져있을 경우 종료
      if($('#updateContent').val() != null){
    	  cancelUpdateReply();
      }
      
      // 대댓글 입력창이 켜져있을 경우 종료
      if($(".ShowAddReComment").length > 0) {
          cancelAddReComment();
      }
      
      // 대댓글 해당하는 댓글(부모댓글)의 reply_no 값
      let cGroup = $('#reComment'+ reply_no).siblings('.reply_innerDiv').children('.replyNo').val();
      
      // 다른 대댓글 수정창이 켜져있을 경우 종료
      if($('#updateReCommentContent').val() != null){
    	  cancleUpdateReComment();
         /* // 다른 대댓글 수정창이 같은 댓글에 있을 때 
         if(cGroup == $('.updateReComment').siblings('.reply_innerDiv').children('.replyNo').val()) {
            cancleUpdateReComment(cGroup);
         } else { // 다른 대댓글 수정창이 다른 댓글에 있을 때
            let otherCGroup = $('.updateReComment').siblings('.reply_innerDiv').children('.replyNo').val();
            cancleUpdateReComment(otherCGroup);
         } */
      }
      
      let replyView = ""
      replyView += "<div align='left' id='updateRecomment"+ reply_no +"' class='updateReComment'><form id='updateReCommentResultFrm' action='${contextPath }/board/updateReComment?num=<%=num2%>'><b>"+ $("#user").val() +"</b><input type='hidden' name='id' value='" + $("#user").val() + "' readonly><br>";
      replyView += "<input type='hidden'  name='write_no'  value='" + $('#write_no').val() + "'>"
      replyView += "<input type='hidden' id='updateReCommentReply_no' name='updateReCommentReply_no' value='" + $('#reComment' + reply_no).children('.replyNo').val() + "'>"
      replyView += "<textarea id='updateReCommentContent' name='updateReCommentContent' rows='5' cols='30' autofocus>" + $('#reComment' + reply_no).children('.repContent').text() + "</textarea><br>"
      replyView += "<div class='replyView'>"
      replyView += "<input type='button' onclick='updateReCommentConfirm()' value='수정'>"
      replyView += "<input type='button' onclick='cancleUpdateReComment()' value='취소'></form><br></div>"
      //"+$('#reComment'+ reply_no).siblings('.reply_innerDiv').children('.replyNo').val()+"
      replyView += "</div>"
      
      htmlStorageRecomment = $('#reComment'+ reply_no).html();
      storagePositionRecomment = reply_no;
      console.log(htmlStorageRecomment);
      
      $('#reComment'+ reply_no).replaceWith(replyView);
      $('#reComment'+ reply_no).children('#updateReCommentContent').focus();   
   }
   
   // 대댓글 수정 확인
   function updateReCommentConfirm(){
      if(!confirm('수정하시겠습니까?')){
         return false;
      } else {
     	 $("div.rep").show();
         $("#updateReCommentResultFrm").submit();
      }
   }
   
   // 대댓글 수정 취소
   function cancleUpdateReComment() {
     /*  $('.updateReComment').replaceWith(""); // 대댓글 수정 div 삭제
      $("#rep" + cGroup).children(".reComment").remove(); // 댓글에 해당하는 대댓글들 모두 삭제 (reComment에서 .reComment가 하나라도 있으면 안 열림)
     $("div.rep").show();
      reComment(cGroup); */
	   $('#updateRecomment'+ storagePositionRecomment).replaceWith("<div class='reComment' align='left' id='reComment"+storagePositionRecomment+"'>" + htmlStorageRecomment + "</div>");
   }
   
   // 대댓글 관련 기능 끝 -------------------------------------------------------------------------------------------------------
   // 찜하기 관련 기능 시작 ------------------------------------------------------------------------------------------------------
   function toggleDibs() {
      let form = {}
      let arr = $("#dibs_info").serializeArray();
      for( i = 0; i <arr.length ; i++) {
         form[arr[i].name] = arr[i].value;
      }
      
      console.log(arr[1].value + "입니다");
      $.ajax({
         url: "toggleDibs/"+$("#write_no").val(),
         type: "POST", 
         data: JSON.stringify(form),
         contentType: "application/json; charset=utf-8",
         success: function(data) {
            if(data.result == 1) { // (처음으로 누른 경우) insert 결과가 1 이거나,(처음이 아닌 경우) dibs_state 가 1 일 때 
               $("#dibs_image").attr("src", "https://cdn-icons-png.flaticon.com/512/138/138533.png?w=826&t=st=1686704293~exp=1686704893~hmac=6f355d28e7dbaf3380f00e77d046efe85cf73ab4f5d2adcf464457a3b814b714");
            }
            if(data.result == 0) { // dibs_state가 0일 때
               $("#dibs_image").attr("src", "https://cdn-icons-png.flaticon.com/512/1222/1222392.png?w=826&t=st=1686704242~exp=1686704842~hmac=c1303f6f53b624870cb23578a1d29c709520f8bab476386e8427893ab06117fb");
            }
            $("#dibsNumChangePoint").html(data.changedDibsNum);
         },
         error: function() {
            alert("찜하기 실패...")
         }
         
      })

   }
   
   // 찜하기 관련 기능 끝 ------------------------------------------------------------------------------------------------------
</script>
<style type="text/css">
#modal_wrap {
   display: none;
   position: fixed;
   z-index: 9;
   margin: 0 auto;
   top: 0; left: 0; right: 0;
   width: 100%;
   height: 100%;
   background: rgba(0, 0, 0, 0.4);
}

#first {
   display: none;
   position: fixed;
   z-index: 10;
   margin: 0 auto;
   top: 60%; left: 0; right: 0;
   width: 50%;
   height: 250px;
   background: rgba(210, 240, 250, 0.9);
}
#first_{
   width: 50%;
   height: 250px;
   padding-top: 10px;
   margin: 0 auto;
}

#second {
   display: none;
   position: fixed;
   z-index: 10;
   margin: 0 auto;
   top: 30px; left: 0; right: 0;
   width: 300px;
   height: 350px;
   background: rgba(210, 240, 250, 0.9);
}

h1 {
   text-align: center;
}

.contentView {
   display: flex;
   justify-content: center;
}

.reply {
   background-color: white;
   width: 100%;
   border: 2px solid #C19681;
   padding: 5px;
   margin-top: 5px;
   border-radius: 5px;
}

.unknownImg {
   margin: 2px;
   width: 40px;
   height: 40px;
   border: 3px solid white;
   border-radius: 20px;
}

hr {
   background: #eaeaea;
   height: 2px;
   border: 0;
}

#updateResultFrm {
   display: flex;
   justify-content: center;
}

#updateResultFrm textarea {
   display: block;
}

.reComment {
   background-color: cookie;
   padding : 5px 30px 5px 30px;
   width: 100%;
   
}
.replyView{
 text-align: right;
}
.replyView input{
   border:none;
   background: none; 
}
.dotdotdot {
   width: 20px;
   height: 20px;
}

.deleteUpdateButton {
   display: inline-block;
   float: right;
   margin-left: 5px;
   color: gray;
   text-align: right;
}
table             { 
  border-spacing: 1; 
  border-collapse: collapse; 
  background:white;
  border-radius:6px;
  overflow:hidden;
  width:100%;
  margin:0 auto;
  position:relative;
  }
    th{ 
    padding-left:8px;
    padding-right: 8px;
    text-align:center;
    }  
    td{ 
    padding-left:8px;
    padding-right: 8px;
    text-align:left;
    }  
 th { background-color: #C19681;
 width: auto;}
  tbody tr     { 
  height:40px; 
  border-bottom:1px solid white;
    &:last-child  { border:0; }
  }
  a{
   text-decoration-line: none;
}
button{
   border:none;
}

  .hidden {
    display: none;
  }
  .left{
     text-align: right;
  }
#rep_but{
   border:none;
   background: none;
}
.rep_but{
   width: 100%;
   height: 40px;
   border-radius: 40px;
   background: #C19681;
   border: none;
   outline: none;
   cursor: pointer;
   font-size: 1em;
   font-weight: 600;
   margin-top: 5px;
   margin-bottom: 5px;
}
.rep{
   border: 1px solid #C19681;
   border-radius: 40px;
   border: none;
}
 textarea{
    border-radius: 5px;
	border: 2px solid #C19681;
    padding: 10px;
    width: 100%;
    height: 100px;
    resize: none;
  }
  .replyView{
  	text-align: right;
  }
  .recom{
  	text-align: right;  
  }
    .recom input{
    border:none;
    background: none;
  }
     .recom button{
    border:none;
    background: none;
  }
  
    .replyView input{
  	margin: 5px;
	border:none;
	background: none;
  }
#recom{
 	font-size: 17px;
  	margin: 5px;
	background: none;
	border: none;
}
.no_reply{
    border-radius: 5px;
  	 border: 2px solid #C19681;
  	 background: white;
     padding: 10px;
     width: 100%;
     height: 100px;
}
#dibsNum{
	background-color:transparent;
	border:none;
}
#dibs_image_{
	background-color:transparent;
	border:none;
}
</style>
</head>
<body onload="replyData()">

   <c:import url="../default/header.jsp"/>
   
      <!-- 관리자 확인 -->
      <div>
         <input type="hidden" id='userGrade' value="${info.grade }">
         <input type="hidden" id="admin" value="${admin }">
      </div>
      
      
      <!-- 답글 수정 페이지 -->
      <div id="modal_wrap">
         <div id="second">
            <div >
               <form id="updateFrm" action="#">
                  <input type="hidden" id="write_no" name="write_no" value="${dto.write_no }">
                  <input type="hidden" id="user" name="id" value="${loginUser }">
                  <b>답글(댓글) 수정 페이지</b>
                  <b>작성자 : ${loginUser }</b>
                  <b>내 용</b><br>
                  <textarea id="content" name="content" rows="5" cols="30"></textarea>
                  <button type="button" onclick="repUpdate()">수 정</button>
                  <button type="button" onclick="slide_hide()">취 소</button>
               </form>
            </div>
         </div>
      </div>
      
      <section ><!-- body -->
         <div class="form-box-list"> <!--  container  -->
         <div class="title" >게시판</div>
         <table>
            <tr>
               <th>제목</th>
                  <td class="form-control input-sm">${dto.title }</td>
               <th>분류</th>
                  <td>            
                  <c:if test="${dto.category == 'informationSharing' }">
                   정보 공유 
                  </c:if>
                  <c:if test="${dto.category == 'friendshipPromotion' }">
                   친목 도모 
                  </c:if>
                  <c:if test="${dto.category == 'petSneak' }">
                   펫 간식 
                  </c:if>
                  <c:if test="${dto.category == 'smallChat' }">
                   잡담 
                  </c:if>
                  <c:if test="${dto.category == 'lookForPetFriend' }">
                   펫프랜드 구합니다 
                  </c:if>
                  <c:if test="${dto.category == 'BeingPetFriend' }">
                   펫프랜드 합니다 
                  </c:if>
               </td>
            </tr>
            <tr>
               <th>글번호</th>
                  <td class="form-control input-sm">${dto.write_no }</td>            
               <th>작성일</th>
                  <td class="form-control input-sm">${dto.savedate }</td>
            </tr>
               <tr>
               <th>작성자</th>
                  <td>${dto.id }</td>
               <th>찜하기</th>
                  <td>
                     <button class="btn btn-light" id="dibsNum">
                        <div id="dibsNumPoint"><div id="dibsNumChangePoint">${dibsNum}</div> 명이 찜했습니다!</div>
                     </button>
                     <button class="btn btn-light"  id="dibs_image_"
                                 onclick= <c:choose>
                                                <c:when test="${id == null && loginUser == null}">"loginPlease()"</c:when>
                                                <c:otherwise>"toggleDibs()"</c:otherwise>
                                             </c:choose>
                     >
                           <img id="dibs_image" width="20px" height="20px" alt="버튼" src=
                           <c:if test="${state == 0 || info.id == null}">
                           "https://cdn-icons-png.flaticon.com/512/1222/1222392.png?w=826&t=st=1686704242~exp=1686704842~hmac=c1303f6f53b624870cb23578a1d29c709520f8bab476386e8427893ab06117fb" 
                           </c:if>
                           <c:if test="${state == 1}">
                           "https://cdn-icons-png.flaticon.com/512/138/138533.png?w=826&t=st=1686704293~exp=1686704893~hmac=6f355d28e7dbaf3380f00e77d046efe85cf73ab4f5d2adcf464457a3b814b714" 
                           </c:if>
                           >
                  </button>
                     <form id="dibs_info" action="" method="post" name="dibs_info">
                        <input type="hidden" name="write_no"  value="${dto.write_no }">
                        <input type="hidden" name="id"  value="${loginUser }">
                     </form>
                  </td>
            </tr>
            <tr>
               <th>내용</th>
               <td colspan="3" >${dto.content }</td>
            </tr>
            <tr>
               <th>이미지</th>
               <td colspan="4">
                  <c:if test="${dto.file_name == 'nan'}">
                     <b>이미지가 없습니다...</b>
                  </c:if>
                  <c:if test="${dto.file_name != 'nan'}">
                     <img src="${contextPath }/board/download?file_name=${dto.file_name}" width="200px" height="200px">
                  </c:if>            
               </td>
            </tr>
         </table>
         <div id="reply" ></div>
         <c:if test="${loginUser != null}">
         <div class="rep">
         <form id="frm" action="#">
                  <input type="hidden" id="write_no" name="write_no" value="${dto.write_no }">
                  <input type="hidden" id="user" name="id" value="${loginUser }">
                  <b>${loginUser } 님</b>
                  <br>
                  <textarea id="content" name="content" rows="5" cols="30"></textarea>
         </form>
                  <input class="rep_but" type="button" value="댓글달기" onclick="rep()">
         </div>
         </c:if> 
         <c:if test="${loginUser == dto.id  ||  info.grade == admin}"><!-- 관리자 작성자 -->
         <ul class="menu">
               <li>
                 <a href="#">메뉴</a>
                 <hr>
				<ul class="submenu">
                    <c:choose>
                     <c:when test="${toMyDibsBoard == 'yes' }">
                        <li><a href="${contextPath }/board/myDibsBoard?num=<%=num2%>">목록</a></li>
                     </c:when>
                     <c:otherwise>
                        <c:choose>
                           <c:when test="${board_category != null}">
                              <li><a href="${contextPath }/board/boardSearchForm?num=<%=num2%>&board_category=${board_category }&board_searchCategory=${board_searchCategory}&board_searchKeyword=${board_searchKeyword}">목록</a></li>
                           </c:when>
                           <%-- 0616_최윤희 추가: 마이페이지 에서 해당 contentView 글목록을 가면 다시 마이페이 목록으로 갑니다. --%>
                           <%-- 해당 boardCheckNum이 마이페이지 커뮤니티 게시판 구별 --%>
                           <c:when test="${myBoardCheckNum == 2}">
                           	  <li><a href="${contextPath }/mypageBoard/write/mypageBoardWriteList?num=${num}">목록</a></li>
                           </c:when>
                           <%-- 0616_최윤희 끝 --%>
                           <c:otherwise>
                              <li><a href="${contextPath }/board/boardAllList?num=<%=num2%>">목록</a></li>
                           </c:otherwise>   
                        </c:choose>
                     </c:otherwise>
                  </c:choose>
                      <hr>
                      <li><a href="${contextPath }/board/modifyForm?write_no=${dto.write_no }&num=<%=num2%>">수정</a></li>
                      <hr>
                      <li><a href="${contextPath }/board/delete?write_no=${dto.write_no }&file_name=${dto.file_name }">삭제</a></li>
                    </ul>
                  </li>
                </ul>
                </c:if> 
               <c:if test="${loginUser != dto.id  &&  info.grade != admin}"><!-- 작성자x 비로그인 -->
               <ul class="menu">
               <li>
                 <a href="#">메뉴</a>
                 <hr>
				<ul class="submenu_">
                    <c:choose>
	                     <c:when test="${toMyDibsBoard == 'yes' }">
	                        <li><a href="${contextPath }/mypageBoard/myDibsBoard?num=<%=num2%>">목록</a></li>
	                     </c:when>
	                     <c:otherwise>
	                        <c:choose>
	                           <c:when test="${board_category != null}">
	                              <li><a href="${contextPath }/board/boardSearchForm?num=<%=num2%>&board_category=${board_category }&board_searchCategory=${board_searchCategory}&board_searchKeyword=${board_searchKeyword}">목록</a></li>
	                           </c:when>
	                           <c:otherwise>
	                              <li><a href="${contextPath }/board/boardAllList?num=<%=num2%>">목록</a></li>
	                           </c:otherwise>   
	                        </c:choose>
	                     </c:otherwise>
	                  </c:choose>
	                  </ul>
	               	</li>
				</ul>
              </c:if>
      </div>
   </section>
   
   <c:import url="../default/footer.jsp"/>
   
</body>
</html>