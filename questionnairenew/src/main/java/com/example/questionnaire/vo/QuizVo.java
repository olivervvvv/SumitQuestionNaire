package com.example.questionnaire.vo;

import java.util.ArrayList;
import java.util.List;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizVo {
	
	//問卷調查，問卷名稱與開始結束時間
	private Questionnaire questionnaire = new Questionnaire();

	//問卷裡面的問題
	@JsonProperty("question_list")
	private List<Question> questionList = new ArrayList<>();

	public QuizVo() {
		super();
	}

	public QuizVo(Questionnaire questionnaire) {
		super();
		this.questionnaire = questionnaire;
	}

	public QuizVo(Questionnaire questionnaire, List<Question> questionList) {
		super();
		this.questionnaire = questionnaire;
		this.questionList = questionList;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
	
}
