package com.upgrad.quora.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.upgrad.quora.service.entity.AnswerEntity;

@Repository
public class AnswerDao {

	@PersistenceContext
	private EntityManager entityManager;

	
	public AnswerEntity getAnswerById(final String answerId) {
		try {
			return entityManager.createNamedQuery("answerById", AnswerEntity.class).setParameter("id", answerId)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public int deleteAnswerById(final String answerId) {
		try {
			return entityManager.createNamedQuery("deleteAnswerById", AnswerEntity.class).setParameter("id", answerId)
					.executeUpdate();
		} catch (NoResultException nre) {
			return -1;
		}
	}

	
	public List<AnswerEntity> getAllAnswersByQuestionId(final String questionId) {
		try {
			 List<AnswerEntity> resultList = entityManager.createNamedQuery("answerByQuestionId", AnswerEntity.class).setParameter("questionId", questionId)
					.getResultList(); 
			 return resultList;
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	
    public AnswerEntity saveAnswer(AnswerEntity answerEntity) {
    	 
    	 try {
			 entityManager.persist(answerEntity);
			 return answerEntity;
		} catch (NoResultException nre) {
			return null;
		} 
    	 
     }

	
}
