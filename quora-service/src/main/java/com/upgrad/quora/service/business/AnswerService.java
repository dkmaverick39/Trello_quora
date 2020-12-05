package com.upgrad.quora.service.business;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;

@Service
public class AnswerService {

	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private UserDao userDao;
	
	public AnswerEntity createAnswer(String accessToken, String answer,String questionUUId) throws AuthorizationFailedException {
		
		 
		//Check if the question with uuid exists or not 
		// and accordingly throw exception
		
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
		
		//answerEntity.setQuestion();
		
		AnswerEntity savedAnswer = answerDao.saveAnswer(answerEntity);  
		
		return savedAnswer;
		
	}
	
	
	public AnswerEntity editAnswerContent(final String accessToken,final String answerUUId,String content) throws AuthorizationFailedException {
		
		UserAuthEntity userAuthByToken = userDao.getUserAuthByToken(accessToken); 
		
		if(userAuthByToken == null) {
			throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
		}
		if(userAuthByToken.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to edit an answer");
        }

		
		AnswerEntity answerEntity =answerDao.getAnswerByUUId(answerUUId);
		
		if(answerEntity == null) {
			throw new AuthorizationFailedException("ANS-001", "Entered answer uuid does not exist");
		}
		if(!answerEntity.getUser().getUuid().equals(userAuthByToken.getUserId().getUuid())) {
			throw new AuthorizationFailedException("ATHR-003", "Only the answer owner can edit the answer");
		}
		
		
		answerEntity.setAnswer(content); 
		
		//ZonedDateTime currentTime = ZonedDateTime.now();
		//answerEntity.setCreatedDate(currentTime); 

		AnswerEntity savedAnswer = answerDao.saveAnswer(answerEntity);  
		
		return savedAnswer; 
		
	}
	
	
	
	public void deleteAnswer(final String accessToken, final String answerUUId) throws AuthorizationFailedException {
        
		UserAuthEntity userAuthByToken = userDao.getUserAuthByToken(accessToken);  
		
		if(userAuthByToken == null) {
			throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
		}
		if(userAuthByToken.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to delete an answer");
        }

		
		AnswerEntity answerEntity =answerDao.getAnswerByUUId(answerUUId);
		
		if(answerEntity == null) {
			throw new AuthorizationFailedException("ANS-001", "Entered answer uuid does not exist");
		}
		
		if(!(answerEntity.getUser().getUuid().equals(userAuthByToken.getUserId().getUuid())
				|| answerEntity.getUser().getRole().equalsIgnoreCase("ADMIN"))) { 
			throw new AuthorizationFailedException("ATHR-003", "Only the answer owner or admin can delete the answer");
		}
		
		answerDao.deleteAnswerByUUId(answerUUId); 
		
	}
	
	
	
	public List<AnswerEntity> getAllAnswers(final String accessToken , final String questionUUId) throws AuthorizationFailedException{
		
		UserAuthEntity userAuthByToken = userDao.getUserAuthByToken(accessToken);   
		
		if(userAuthByToken == null) {
			throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
		}
		if(userAuthByToken.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get the answers");
        }

        //Check if questionUUId exists or not
		// if not throw exception
		
		List<AnswerEntity> allAnswersByQuestionId = answerDao.getAllAnswersByQuestionUUId(questionUUId); 
		return allAnswersByQuestionId;
		
	}
	
	
	
}
