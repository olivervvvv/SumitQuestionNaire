package com.example.questionnaire.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.Service.ifs.QuizService;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizSearchReq;

@CrossOrigin
@RestController
public class QuizController {

	@Autowired
	private QuizService service;
	
	
	@PostMapping(value = "quiz/create")
	public QuizRes create(@RequestBody QuizReq req) {
//		System.out.println("title:"+req.getQuestionnaire().getTitle());
		return service.create(req);
	}
	@PostMapping(value = "quiz/createQuestion")
	public QuizRes createQuestion(@RequestBody QuizReq req) {
//		System.out.println("title:"+req.getQuestionnaire().getTitle());
		return service.createQuestion(req);
	}
	
//	@GetMapping(value = "quiz/search")
//	public QuizRes search(@RequestParam(required = false) QuizSearchReq req) {
//		return service.search(req.getTitle(),req.getStartDate(),req.getEndDate());
//	}
	@GetMapping(value = "quiz/search")
	public QuizRes search(
			
	    @RequestParam(required = false) String title,
	    @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	    @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
	) {
	    // �p�G�ݭn�ҽk�j�M�A�A�i�H�b�o�̨ϥμҽk�j�M���޿�
	    // �o�u�O�@��²�檺�Ҥl�A��ڱ��p�i��ݭn�ھڧA���ƾڮw�����i��վ�
		System.out.println("Received title: " + title);
	    System.out.println("Received startDate: " + startDate);
	    System.out.println("Received endDate: " + endDate);
		System.out.println(title);
	    return service.search(title, startDate, endDate);
	    }
	
	
	@GetMapping(value = "quiz/searchQuestionnaireList")
	public QuestionnaireRes searchQuestionnaireList(
			@RequestParam(required = false) String title, 
			@RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate startDate, 
			@RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endDate 
			){
		System.out.println("Received title: " + title);
	    System.out.println("Received startDate: " + startDate);
	    System.out.println("Received endDate: " + endDate);
	    return service.searchQuestionnaireListEazy(title, startDate, endDate);
		
	}
	
	@GetMapping(value = "quiz/get")
	public QuizRes getquestion(
			@RequestParam(required = false) int id
			) {
				return service.getquestion(id);
		
	}
	@GetMapping(value = "quiz/questions")
    public ResponseEntity<QuizRes> getQuestions(@RequestParam(required = false)Integer qnId) {
        
		QuizRes quizRes = service.getQuestionsByQnId(qnId);
        return new ResponseEntity<>(quizRes, HttpStatus.OK);
    }
		

}
