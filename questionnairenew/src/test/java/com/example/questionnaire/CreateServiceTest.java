package com.example.questionnaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.questionnaire.Service.ifs.QuizService;
import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.repository.QuestionDao;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;

@SpringBootTest
public class CreateServiceTest {

	@Autowired
	private QuizService quizService;
	
	@Autowired
	private QuestionDao quDao;

	@Autowired
	private QuestionnaireDao qnDao;

	@Test
	public void CreateTest1() {
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setTitle("BQuiz");
		questionnaire.setPublished(true);
		questionnaire.setDescription("AAAAAAAAAA");
		questionnaire.setStartDate(LocalDate.of(2023, 12, 1));
		questionnaire.setEndDate(LocalDate.of(2023, 12, 25));
		QuizReq req = new QuizReq();
//		req.setQuestionnaire(questionnaire);
		
		List<Question> questionList = new ArrayList<>();
		Question qu = new Question(1,0,"QuTitle","Text",true,"Option");
		questionList.add(qu);
		qu = new Question(1,0,"AQuTitle","Text",true,"Option");
		questionList.add(qu);
		qu = new Question(2,0,"BQuTitle","Text",true,"Option");
		questionList.add(qu);
		qu = new Question(3,0,"CQuTitle","Text",true,"Option");
		questionList.add(qu);
		qu = new Question(4,0,"DQuTitle","Text",true,"Option");
		questionList.add(qu);
		req.setQuestionnaire(questionnaire);
		req.setQuestionList(questionList);
		
		System.out.println(quizService.create1(req).getRtnCode());
	
		
	}
	@Test
	public void updateTest1() {
		// 準備測試數據
	    QuizReq quizReq = new QuizReq();
	    
	    // 設置問卷信息
	    Questionnaire questionnaire = new Questionnaire();
	    questionnaire.setTitle("Sample Quiz");
	    quizReq.setQuestionnaire(questionnaire);

	    // 設置問題信息
	    List<Question> questionList = new ArrayList<>();
	    Question question1 = new Question();
	    question1.setqTitle("What is your favorite color?");
	    question1.setOptionsType("Blue");
	    question1.setOptionsType("Red");
	    question1.setOptionsType("");
	    questionList.add(question1);

//	    Question question2 = new Question();
//	    question2.setOptionsType("How satisfied are you with our service?");
//	    question2.setOptions("Very Satisfied");
//	    questionList.add(question2);

	    quizReq.setQuestionList(questionList);

	    // 呼叫被測試的方法
	    QuizRes result = quizService.create(quizReq);

	    // 進行斷言 (Assertions) 來確認預期結果
	    assertNotNull(result);
//	    assertEquals(RtnCode.SUCCESSFUL, result.getRtnCode());
//	    assertEquals(RtnCode.UPDATE_ERROR, result.getRtnCode());


	}
	 @Test
	 @Transactional
	 public void testDeleQuestionnaire() {
	        // 準備測試數據
	        List<Integer> qnIdList = Arrays.asList(1, 2, 3);

	        // 呼叫被測試的方法
	        QuizRes result = quizService.deleQuestionnaire(qnIdList);

	        // 進行斷言 (Assertions) 來確認預期結果
	        assertNotNull(result);
	        assertEquals(RtnCode.SUCCESSFUL, result.getRtnCode());
	    }
	 @Test
		public void searchQuestionnaireList() {
		 QuestionnaireRes result = quizService.searchQuestionnaireListEazy("白",LocalDate.of(2023, 1, 1),LocalDate.of(2023,12,30));
		 List<Questionnaire> questionList = result.getQuestionnaire();
		 for (Questionnaire questionnaire : questionList) {
		        System.out.println("ID: " + questionnaire.getId());
		        System.out.println("Title: " + questionnaire.getTitle());
		        System.out.println("Description: " + questionnaire.getDescription());
		        System.out.println("Published: " + questionnaire.isPublished());
		        System.out.println("StartDate: " + questionnaire.getStartDate());
		        System.out.println("EndDate: " + questionnaire.getEndDate());
		        System.out.println("------");
		    }
	 }
	 @Test
		public void insertTest() {
		 int res = qnDao.insert("hel","hie",false,LocalDate.parse("2023-08-23"),LocalDate.parse("2023-11-14"));
		 System.out.println("=================");
		 System.out.print(res);
	 }
	 @Test
		public void updateTest() {
		 int res = qnDao.updateData(27, "破局了!!!","真的翻桌了" );
		 System.out.print(res);
	 }
	 
	 @Test
	 public void testGetQuestion() {
	     int id = 52;

	     Optional<Questionnaire> qnOp = qnDao.findById(id);

	     if (qnOp.isEmpty()) {
	         QuizRes result = new QuizRes(null, null, RtnCode.ID_NOTFOUNT);
	         System.out.println("Result: " + result);
	         return;
	     }

	     List<Question> questions = quDao.findAllByQuId(id);

//	     QuizRes result = new QuizRes(questions, RtnCode.SUCCESSFUL);
//	     System.out.println("Result: " + result);
	 }
	 
	 @Test
	 @Transactional
	 public void testGetQuestion1() {
		 List<Question> questionList = new ArrayList<>();
		 List<Question> res = quDao.findByQnId(60);
		 System.out.println("++++++++++++============s");
		 System.out.println(res);
		 for (Question question : res) {
		        System.out.println("Question ID: " + question.getqnId());
		        System.out.println("Quiz ID: " + question.getQuId());
		        System.out.println("Question Title: " + question.getqTitle());
		        // 输出其他属性...
		    }
	 }
    
}
