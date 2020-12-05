package com.upgrad.quora.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AnswerController {

	
	 @RequestMapping(value="/question/{questionId}/answer/create" , method = RequestMethod.POST)
	 public void createAnswer() {
		 
	 }
	
	 @RequestMapping(value="/answer/edit/{answerId}" , method = RequestMethod.POST)
	 public void editAnswerContent() {
		 
	 }
	 
	 
	 @RequestMapping(value="/answer/delete/{answerId}" , method = RequestMethod.POST)
	 public void deleteAnswer() {
		 
	 }
	 
	 @RequestMapping(value="/answer/all/{questionId}" , method = RequestMethod.POST)
	 public void getAllAnswersToQuestion() {
		 
	 }
	 
	 
}
