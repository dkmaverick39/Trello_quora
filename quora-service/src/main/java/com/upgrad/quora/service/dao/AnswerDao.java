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

	
	public AnswerEntity getAnswerByUUId(final String answerByUUId) {
		try {
			return entityManager.createNamedQuery("answerByUUId", AnswerEntity.class).setParameter("uuid", answerByUUId)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public int deleteAnswerByUUId(final String answerUUId) {
		try {
			return entityManager.createNamedQuery("deleteAnswerByUUId", AnswerEntity.class).setParameter("uuid", answerUUId)
					.executeUpdate();
		} catch (NoResultException nre) {
			return -1;
		}
	}

	
	public List<AnswerEntity> getAllAnswersByQuestionUUId(final String questionUUId) {
		try {
			 List<AnswerEntity> resultList = entityManager.createNamedQuery("answerByQuestionUUId", AnswerEntity.class).setParameter("questionUUId", questionUUId)
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
