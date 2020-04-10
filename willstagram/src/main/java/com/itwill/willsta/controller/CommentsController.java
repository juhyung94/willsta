package com.itwill.willsta.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.itwill.willsta.domain.Comments;
import com.itwill.willsta.service.CommentsService;

@RestController
public class CommentsController {
	@Autowired
	private CommentsService commentsService;
	
	@MemberLoginCheck
	@PostMapping(value = "/commentsInsert", produces = "text/plain;charset=UTF-8")
	public String commentsInsert(@RequestParam(value = "pNo", defaultValue = "15") int pNo,
									   		 @RequestParam String cContents,
									   		 HttpSession session) throws Exception {
		String result = "";
		Comments comments = new Comments();
		String mId = (String)session.getAttribute("mId");
		comments.setpNo(pNo);
		comments.setmId(mId);
		comments.setcContents(cContents);
		int createResult = commentsService.createComment(comments);
		if(createResult == 1) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}
	
	@MemberLoginCheck
	@PostMapping(value = "/postCommentsList", produces = "text/plain;charset=UTF-8")
	public String postCommentsList(@RequestParam(value = "pNo") int pNo) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Comments> postCommentsList = commentsService.postCommentsList(pNo);
		for (int i = 0; i < postCommentsList.size(); i++) {
			Comments comments = postCommentsList.get(i);
			sb.append("<div class='comment-sec' style='display:none;' comments_no='"+comments.getcNo()+"'>");
			sb.append("<ul>");
			sb.append("	<li>");
			sb.append("		<div class='comment-list'>");
			sb.append("			<div class='comment'>");
			sb.append("				<h3>"+comments.getmId()+"</h3>");
			sb.append("				<span><img src='images/clock.png' alt=''>"+comments.getcTime()+"</span>");
			sb.append("				<p>"+comments.getcContents()+"</p>");
			sb.append("				<a href='#' class='active active-reply' comments_no='"+comments.getcNo()+"'>");
			sb.append("					<i class='fa fa-reply-all'> Reply</i></a>");
			sb.append("			</div>");
			sb.append(" 	</div>");	
			sb.append("	</li>");
			sb.append("</ul>");
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	@MemberLoginCheck
	@PostMapping(value = "/reCommentsInsert", produces = "text/plain;charset=UTF-8")
	public String reCommentsInsert(@RequestParam(value = "cNo") int cNo,
								   @RequestParam(value = "pNo") int pNo,
								   @RequestParam String cContents,
								   HttpSession session) throws Exception {
		String result = "";
		Comments comments = new Comments();
		String mId = (String)session.getAttribute("mId");
		comments.setcNo(cNo);
		comments.setpNo(pNo);
		comments.setmId(mId);
		comments.setcContents(cContents);
		int createResult = commentsService.createComment(comments);
		if(createResult == 1) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}
	
	public String commentsUpdate() throws Exception {
		
		return "";
	}
	
	public String commentsDelete() throws Exception {
		
		return "";
	}
}
