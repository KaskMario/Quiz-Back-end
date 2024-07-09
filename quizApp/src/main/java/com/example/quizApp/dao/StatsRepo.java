package com.example.quizApp.dao;

import com.example.quizApp.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatsRepo extends JpaRepository<QuizResult, Integer> {

    @Query(value = "SELECT COUNT(*) FROM `quiz-results` q WHERE q.user_id = :userId", nativeQuery = true)
    Integer countGamesByUserId(@Param("userId") int userId);

    @Query(value = "SELECT DISTINCT q.user_id FROM `quiz-results` q;", nativeQuery = true)
    List<Integer> getPlayedUsers();

    @Query(value = "SELECT DISTINCT q.category FROM `quiz-results` q WHERE q.user_id = :userId;", nativeQuery = true)
    List<String> getUsersCategories(@Param("userId") int userId);

    @Query(value = "SELECT COUNT(*) FROM `quiz-results` q WHERE q.difficulty = :difficulty AND q.user_id = :userId", nativeQuery = true)
    int countByDifficulty(@Param("difficulty") String difficulty, @Param("userId") int userId);


    @Query(value = "SELECT q.category, COUNT(*) AS category_count FROM `quiz-results` q WHERE q.user_id = :userId GROUP BY q.category", nativeQuery = true)
    List<Object[]> findCategoryCountsByUserId(@Param("userId") int userId);

    @Query(value = "SELECT COUNT(*) FROM `quiz-results` q WHERE q.length = :length AND q.user_id = :userId", nativeQuery = true)
    int countByLength(@Param("length") int length, @Param("userId") int userId);


  @Query(value= "SELECT q.length, COUNT(*) AS length_count FROM `quiz-results` q WHERE q.user_id = :userId GROUP BY q.length", nativeQuery = true)
  List<Object[]> findLengthCountsByUserId(@Param("userId") int userId);

  @Query(value = "SELECT SUM(q.length) FROM `quiz-results` q WHERE q.user_id = :userId AND q.difficulty = :difficulty", nativeQuery = true)
  Integer sumUpQuestions(@Param("difficulty") String difficulty, @Param("userId")int userId);

    @Query(value = "SELECT SUM(q.correct_answers) FROM `quiz-results` q WHERE q.user_id = :userId AND q.difficulty = :difficulty", nativeQuery = true)
    Integer sumUpRightAnswers(@Param("difficulty") String difficulty, @Param("userId")int userId);


    @Query(value = "SELECT DATE_FORMAT(MAX(q.created_at), '%d-%m-%Y %H:%i') FROM `quiz-results` q WHERE q.user_id = :userId", nativeQuery = true)
    String findMostRecentDate(@Param("userId")int userId);

    @Query(value = "SELECT COUNT(*) FROM `quiz-results` q WHERE q.is_flawless = :isFlawless AND q.user_id = :userId", nativeQuery = true)
    Integer sumUpFlawless(@Param("isFlawless") boolean isFlawless, @Param("userId")int userId);

    @Query(value = "SELECT DISTINCT q.difficulty FROM `quiz-results` q WHERE q.user_id = :userId;", nativeQuery = true)
    List<String> getUsersDifficulties(@Param("userId") int userId);






}