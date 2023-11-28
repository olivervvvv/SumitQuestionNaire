package com.example.questionnaire.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Questionnaire;

@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire, Integer>{
	
//	public Questionnaire findTopByOrderById();
	public Optional<Questionnaire> findById(int id);
	
	public List<Questionnaire> findByIdIn(List<Integer> idList);
	
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String title, LocalDate start,LocalDate end);
	
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(String title, LocalDate start,LocalDate end);

	@Modifying
	@Transactional
	@Query(value="insert into questionnaires(title, description, is_published, start_date, end_date) "
	        + "values(:title, :desp, :isPublished, :startD, :endD)", nativeQuery = true)
	public int insert(
	        @Param("title") String title,
	        @Param("desp") String description,
	        @Param("isPublished") boolean isPublished,
	        @Param("startD") LocalDate startDate,
	        @Param("endD") LocalDate endDate);
	
	@Modifying
	@Transactional
	@Query(value="insert into questionnaires(title, description, is_published, start_date, end_date) "
	        + "values(?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	public int insertData(
	         String title, //
	         String description, //
	         boolean isPublished, //
	         LocalDate startDate, //
	         LocalDate endDate); 
	
	/**
	 * clearAutomatically清除暫存資料，再去作方法
	 **/
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update questionnaires set title = :title, description = :des"
			+ " where id= :id", nativeQuery = true)
	public int updateData(
			@Param("id") int id,
	        @Param("title") String title,
	        @Param("des") String description);
	
	//==================================
	//select
	
//	@Query(value = "select id, title, is_published from questionnaires"
//			+ " where start_date >:startDate", nativeQuery = true)
//	public List<Questionnaire> findByData(
//			@Param("id") int id,
//	        @Param("title") String title,
//	        @Param("des") String description);

//	@Query(value = "select new Questionnaire(id, title, published)"
//			+ " from Questionnaire where start_date >:startDate")
//	public List<Questionnaire> findByStartDate2();
//	
	//order by + limit(limit 語法只能使用在nativeQuery =true)
//	@Query(value = "select * from questionnaire as qu"
//			+ " where start_date >:startDate or is_published = :isPublished order by id desc limit :num"
//			, nativeQuery = true)
//	public List<Questionnaire> findByStartDate5(
//			@Param("startDate") LocalDate startDate,
//	        @Param("isPublished")boolean isPublished ,
//	        @Param("num") int num
//			);
	
}
