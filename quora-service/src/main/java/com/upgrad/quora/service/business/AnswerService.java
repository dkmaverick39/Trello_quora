package com.upgrad.quora.service.business;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.QuestionNotFoundException;

@Service
public class AnswerService {

	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private UserDao userDao;
	
	
	@Autowired
	private QuestionDao questionDao;
	
	public AnswerEntity createAnswer(String accessToken, String answer,String questionUUId) throws AuthorizationFailedException, QuestionNotFoundException {
		
		 
		//Check if the question with uuid exists or not 
		// and accordingly throw exception
		 
		QuestionEntity question = questionDao.getQuestionById(questionUUId); 
		if(question == null) {
			throw new QuestionNotFoundException("QUES-001", "The question entered is invalid");
		}
		UserAuthEntity userAuthByToken = userDao.getUserAuthByToken(accessToken);
		
		if(userAuthByToken == null) {
			throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
		}
		if(userAuthByToken.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to post an answer");
        }
		
		AnswerEntity answerEntity = new AnswerEntity();
		answerEntity.setAnswer(answer); 
		answerEntity.setUuid(UUID.randomUUID().toString());

		ZonedDateTime currentTime = ZonedDateTime.now();
		
		answerEntity.setCreatedDate(currentTime); 

		answerEntity.setUser(userAuthByToken.getUserId()); 
		
		answerEntity.setQuestion(question);
		
		AnswerEntity savedAnswer = answerDao.saveAnswer(answerEntity);  
		
		return savedAnswer;
		
	}
	
	
	public AnswerEntity editAnswerContent(final String accessToken,final String answerUUId,String content) throws AuthorizationFailedException, AnswerNotFoundException {
		
        AnswerEntity answerEntity =answerDao.getAnswerByUUId(answerUUId);
		
		if(answerEntity == null) {
			throw new AnswerNotFoundException("ANS-001", "Entered answer uuid does not exist");
		}
		UserAuthEntity userAuthByToken = userDao.getUserAuthByToken(accessToken);  
		
		if(userAuthByToken == null) {  
			throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
		}
		if(userAuthByToken.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to edit an answer");
        }

		
		
		if(!answerEntity.getUser().getUuid().equals(userAuthByToken.getUserId().getUuid())) {
			throw new AuthorizationFailedException("ATHR-003", "Only the answer owner can edit the answer");
		}
		
		
		answerEntity.setAnswer(content); 
		
		ZonedDateTime currentTime = ZonedDateTime.now();
		answerEntity.setCreatedDate(currentTime); 

		AnswerEntity savedAnswer = answerDao.saveAnswer(answerEntity);  
		
		return savedAnswer; 
		
	}
	
	
	
	public void deleteAnswer(final String accessToken, final String answerUUId) throws AuthorizationFailedException, AnswerNotFoundException {
        
		UserAuthEntity userAuthByToken = userDao.getUserAuthByToken(accessToken);  
		
		if(userAuthByToken == null) { 
			throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
		}
		if(userAuthByToken.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to delete an answer");
        }

		
		AnswerEntity answerEntity =answerDao.getAnswerByUUId(answerUUId);
		
		if(answerEntity == null) {
			throw new AnswerNotFoundException("ANS-001", "Entered answer uuid does not exist");
		}
		
		if(!(answerEntity.getUser().getUuid().equals(userAuthByToken.getUserId().getUuid())
				|| answerEntity.getUser().getRole().equalsIgnoreCase("ADMIN"))) { 
			throw new AuthorizationFailedException("ATHR-003", "Only the answer owner or admin can delete the answer");
		}
		
		answerDao.deleteAnswerByUUId(answerUUId); 
		
	}
	
	
	
	public List<AnswerEntity> getAllAnswers(final String accessToken , final String questionUUId) throws AuthorizationFailedException, QuestionNotFoundException{
		
		UserAuthEntity userAuthByToken = userDao.getUserAuthByToken(accessToken);   
		
		if(userAuthByToken == null) { 
			throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
		}
		if(userAuthByToken.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get the answers");
        }

        //Check if questionUUId exists or not
		// if not throw exception
		QuestionEntity question = questionDao.getQuestionById(questionUUId); 
		if(question == null) {
			throw new QuestionNotFoundException("QUES-001", "The question with entered uuid whose details are to be seen does not exist");
		}
		
		List<AnswerEntity> allAnswersByQuestionId = answerDao.getAllAnswersByQuestionUUId(questionUUId); 
		return allAnswersByQuestionId;
		
	}
	
	
	
}
