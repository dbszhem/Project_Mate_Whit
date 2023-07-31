package com.web.root.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.root.board.dto.BoardRepDTO;
import com.web.root.board.service.BoardService;
import com.web.root.member.service.MemberService;

@RestController
@RequestMapping("/board")
public class BoardRepController {

	@Autowired
	BoardService bs;
	
	@Autowired
	MemberService ms;
	
	
	// 댓글 기능 -----------------------------------------------------------------------------------------------
	@PostMapping(value="addReply", produces="application/json; charset=utf-8")
	@ResponseBody
	public int addReply(@RequestBody Map<String, Object> map) {
		int result = bs.addReply(map);
		return result;
	}
	
	@GetMapping(value="replyData/{write_group}", produces="application/json; charset=utf-8")
	@ResponseBody
	public List<BoardRepDTO> showRep(@PathVariable("write_group") int write_group){
		return bs.getRepList(write_group);
	}
	
	@RequestMapping("deleteReply")
	public void deleteReply(HttpServletRequest request, HttpServletResponse response)  throws IOException {
		String message = bs.deleteReply(request);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message);
		
	}
	
	@RequestMapping("updateReply")
	public void updateReply(Model model,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String message = bs.updateReply(request);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message);
	}
	
	// 대댓글 기능 -------------------------------------------------------------------------------------------
	// 대댓글 불러오기
	@PostMapping(value="reCommentData/{reply_no}", produces="application/json; charset=utf-8")
	@ResponseBody
	public List<BoardRepDTO> reCommentData(@PathVariable("reply_no") int reply_no){
		return bs.getReCommentList(reply_no);
	}
	
	// 대댓글 입력
	@PostMapping(value="addReComment", produces="application/json; charset=utf-8")
	@ResponseBody
	public int addReComment(@RequestBody Map<String, Object> map) {
		int result = bs.addReComment(map);
		return result;
	}
	
	// 대댓글 수정
	@RequestMapping("updateReComment")
	public void updateReComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String message = bs.updateReComment(request);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message);
	}
}
