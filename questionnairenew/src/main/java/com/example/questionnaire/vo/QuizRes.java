package com.example.questionnaire.vo;

import java.util.List;
import java.util.Optional;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;

public class QuizRes {

	private List<QuizVo> quizVoList;
	
	private Optional<Questionnaire> questionnaire;
	
    private List<Question> questions;
	
	private RtnCode rtnCode;


	public QuizRes( RtnCode rtnCode) {
		super();
		this.rtnCode = rtnCode;
	}
	public QuizRes(List<QuizVo> quizVoList, RtnCode rtnCode) {
		super();
		this.quizVoList = quizVoList;
		this.rtnCode = rtnCode;
	}
	 public QuizRes(List<Question> questions) {
	        this.questions = questions;
	    }
	
	public QuizRes(Optional<Questionnaire> questionnaire2, List<Question> questions, RtnCode rtnCode) {
		super();
		this.questionnaire = questionnaire2;
        this.questions = questions;
        this.rtnCode = rtnCode;
    }

	public QuizRes(Optional<Questionnaire> qnOp, List<Question> questions2) {
		this.questionnaire = qnOp;
        this.questions = questions2;
	}
	public Optional<Questionnaire> getQuestionnaire() {
		return questionnaire;
	}


	public void setQuestionnaire(Optional<Questionnaire> questionnaire) {
		this.questionnaire = questionnaire;
	}


	public List<Question> getQuestions() {
		return questions;
	}


	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}


	public List<QuizVo> getQuizVoList() {
		return quizVoList;
	}


	public void setQuizVoList(List<QuizVo> quizVoList) {
		this.quizVoList = quizVoList;
	}


	public RtnCode getRtnCode() {
		return rtnCode;
	}


	public void setRtnCode(RtnCode rtnCode) {
		this.rtnCode = rtnCode;
	}

	

	
	
}
