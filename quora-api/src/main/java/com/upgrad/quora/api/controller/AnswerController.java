package com.upgrad.quora.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.quora.api.model.AnswerDeleteResponse;
import com.upgrad.quora.api.model.AnswerDetailsResponse;
import com.upgrad.quora.api.model.AnswerEditRequest;
import com.upgrad.quora.api.model.AnswerEditResponse;
import com.upgrad.quora.api.model.AnswerRequest;
import com.upgrad.quora.api.model.AnswerResponse;
import com.upgrad.quora.service.business.AnswerService;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.QuestionNotFoundException;

@RestController
@RequestMapping("/")
public class AnswerController {

	
	 @Autowired
	 private AnswerService answerService;
	 

	@RequestMapping(value = "/question/{questionId}/answer/create", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<AnswerResponse> createAnswer(@RequestHeader("authorization") final String accessToken,
			@PathVariable("questionId") String questionUUId, final AnswerRequest answerRequest)
			throws AuthorizationFailedException, QuestionNotFoundException { 

		String answer = answerRequest.getAnswer();
		
		AnswerEntity createdAnswer = answerService.createAnswer(accessToken, answer, questionUUId);

		AnswerResponse answerResponse = new AnswerResponse();
		answerResponse.setId(createdAnswer.getUuid());
		answerResponse.setStatus("ANSWER CREATED");

		return new ResponseEntity<AnswerResponse>(answerResponse, HttpStatus.CREATED);

	}
	
	@RequestMapping(value = "/answer/edit/{answerId}", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<AnswerEditResponse> editAnswerContent(
			@RequestHeader("authorization") final String accessToken, @PathVariable("answerId") String answerId,
			final AnswerEditRequest editRequest) throws AuthorizationFailedException, AnswerNotFoundException {
		  
		 String content = editRequest.getContent(); 
		 AnswerEntity editAnswerContent = answerService.editAnswerContent(accessToken, answerId, content);
		 
		 AnswerEditResponse  editResponse = new AnswerEditResponse(); 
		 editResponse.setId(editAnswerContent.getUuid()); 
		 editResponse.setStatus("ANSWER EDITED"); 

		 return new ResponseEntity<AnswerEditResponse>(editResponse, HttpStatus.OK );
		 
	 }
	 
	 
	 @RequestMapping(value="/answer/delete/{answerId}" , method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseEntity<AnswerDeleteResponse> deleteAnswer(@RequestHeader("authorization") final String accessToken,
			 @PathVariable("answerId") String answerUUId) throws AuthorizationFailedException, AnswerNotFoundException {
		 
		 answerService.deleteAnswer(accessToken,answerUUId);  
		  
		 AnswerDeleteResponse deleteResponse = new AnswerDeleteResponse();
		 deleteResponse.setId(answerUUId);
		 deleteResponse.setStatus("ANSWER DELETED");
 		 
		 return new ResponseEntity<AnswerDeleteResponse>(deleteResponse, HttpStatus.OK); 
		 
	 }
	 
	 
	 @RequestMapping(value="/answer/all/{questionId}" , method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseEntity<List<AnswerDetailsResponse>> getAllAnswersToQuestion(@RequestHeader("authorization") final String accessToken,
			 @PathVariable("questionId") String questionUUId ) throws AuthorizationFailedException, QuestionNotFoundException {
		  
		 List<AnswerEntity> allAnswers = answerService.getAllAnswers(accessToken,questionUUId);   
		 
		 List<AnswerDetailsResponse> detailsResponseList = new ArrayList<AnswerDetailsResponse>();
		 
		 for(AnswerEntity answerEntity : allAnswers) {
		    
			  AnswerDetailsResponse detailsResponse = new AnswerDetailsResponse();
			  detailsResponse.setId(answerEntity.getUuid()); 
			  detailsResponse.setAnswerContent(answerEntity.getAnswer()); 
			  detailsResponse.setQuestionContent(answerEntity.getQuestion().getContent()); 
			  detailsResponseList.add(detailsResponse);
		 }
		  
		 return new ResponseEntity<List<AnswerDetailsResponse>>(detailsResponseList, HttpStatus.OK );
		 
	 }
	 
	 
	 
}
