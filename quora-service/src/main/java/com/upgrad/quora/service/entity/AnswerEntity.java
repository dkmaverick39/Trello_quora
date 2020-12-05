package com.upgrad.quora.service.entity;

import java.time.ZonedDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="answer")
@NamedQueries ({
    @NamedQuery(name = "answerById", query = "select u from AnswerEntity u where u.id = :id"),
    //@NamedQuery(name = "answerByQuestionId", query = "select u from AnswerEntity u where u.question.id = :questionId"),
    @NamedQuery(name = "deleteAnswerById", query = "delete from AnswerEntity u where u.id = :id")
})
public class AnswerEntity {

	 @Id
	 @Column(name = "ID")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer id;
	 
	 
	 @Column(name = "UUID")
	 @NotNull
	 @Size(max=200)
	 private String uuid;
	 
	 @Column(name = "ans")
	 @NotNull
	 @Size(max=250)
	 private String answer;
	 
	 
	 @Column(name = "date")
	 private ZonedDateTime createdDate;

	 
//	 @ManyToOne
//	 @JoinColumn(name = "USER_ID")
//	 private UserEntity user;
//	 
//	 @ManyToOne
//	 @JoinColumn(name = "QUESTION_ID")
//	 private QuestionEntity question;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}


	 
	 
	 
	 
}
