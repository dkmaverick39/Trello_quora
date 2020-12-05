package com.upgrad.quora.service.business;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.entity.AnswerEntity;

@Service
public class AnswerService {

	@Autowired
	private AnswerDao answerDao;
	
	public AnswerEntity createAnswer(String authorization, String answer,String questionId) {
		
		AnswerEntity answerEntity = new AnswerEntity();
		answerEntity.setAnswer(answer); 
		
		ZonedDateTime currentTime = ZonedDateTime.now();
		
		answerEntity.setCreatedDate(currentTime); 
		//answerEntity.setUuid(uuid); 
		//answerEntity.setUser();
		//answerEntity.setQuestion();
		
		AnswerEntity savedAnswer = answerDao.saveAnswer(answerEntity);  
		
		return savedAnswer;
		
	}
	
	
	public AnswerEntity editAnswerContent(final String authorization,final String answerId,String content) {
		AnswerEntity answerEntity =answerDao.getAnswerById(answerId);
		answerEntity.setAnswer(content); 
		
		//ZonedDateTime currentTime = ZonedDateTime.now();
		//answerEntity.setCreatedDate(currentTime); 
		//answerEntity.setUuid(uuid); 
		//answerEntity.setUser();
        AnswerEntity savedAnswer = answerDao.saveAnswer(answerEntity);  
		
		return savedAnswer; 
		
	}
	
	
	
	public void deleteAnswer(final String answerId) {
		int deleteAnswerById = answerDao.deleteAnswerById(answerId); 
	}
	
	
	
	public List<AnswerEntity> getAllAnswers(final String questionId){
		List<AnswerEntity> allAnswersByQuestionId = answerDao.getAllAnswersByQuestionId(questionId); 
		return allAnswersByQuestionId;
	}
	
	
	
}
