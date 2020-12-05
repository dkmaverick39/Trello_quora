package com.upgrad.quora.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.quora.api.model.AnswerEditRequest;
import com.upgrad.quora.api.model.AnswerEditResponse;
import com.upgrad.quora.api.model.AnswerRequest;
import com.upgrad.quora.api.model.AnswerResponse;
import com.upgrad.quora.service.business.AnswerService;
import com.upgrad.quora.service.entity.AnswerEntity;

@RestController
@RequestMapping("/")
public class AnswerController {

	
	 @Autowired
	 private AnswerService answerService;
	 
	 @RequestMapping(value="/question/{questionId}/answer/create" , method = RequestMethod.POST)
	 public ResponseEntity<AnswerResponse> createAnswer(@RequestHeader("authorization") final String authorization,
			 @PathVariable("questionId") String questionId,@RequestBody  AnswerRequest answerRequest) {
		 
		 String answer = answerRequest.getAnswer();
		 AnswerEntity createdAnswer = answerService.createAnswer(authorization, answer, questionId);  
		 
		 AnswerResponse  answerResponse = new AnswerResponse();
		 //answerResponse.setId(createdAnswer.getId());
		 //answerResponse.set
		 
		 return new ResponseEntity<AnswerResponse>(answerResponse, HttpStatus.CREATED );
		 
	 }
	
	 @RequestMapping(value="/answer/edit/{answerId}" , method = RequestMethod.PUT)
	 public ResponseEntity<AnswerEditResponse> editAnswerContent(@RequestHeader("authorization") final String authorization,
			 @PathVariable("answerId") String answerId, @RequestBody AnswerEditRequest editRequest) {
		 
		 String content = editRequest.getContent(); 
		 AnswerEntity editAnswerContent = answerService.editAnswerContent(authorization, answerId, content);
		 
		 AnswerEditResponse  editResponse = new AnswerEditResponse();
		 

		 return new ResponseEntity<AnswerEditResponse>(editResponse, HttpStatus.OK );
		 
	 }
	 
	 
	 @RequestMapping(value="/answer/delete/{answerId}" , method = RequestMethod.DELETE)
	 public void deleteAnswer(@RequestHeader("authorization") final String authorization,
			 @PathVariable("answerId") String answerId) {
		 
		 answerService.deleteAnswer(answerId); 
		// return new ResponseEntity<AnswerEditResponse>(editResponse, HttpStatus.OK );
	 }
	 
	 
	 @RequestMapping(value="/answer/all/{questionId}" , method = RequestMethod.GET)
	 public ResponseEntity<List<AnswerEntity>> getAllAnswersToQuestion(@RequestHeader("authorization") final String authorization,
			 @PathVariable("questionId") String questionId ) {
		 
		 List<AnswerEntity> allAnswers = answerService.getAllAnswers(questionId);  
		 return new ResponseEntity<List<AnswerEntity>>(allAnswers, HttpStatus.OK );
		 
	 }
	 
	 
}
