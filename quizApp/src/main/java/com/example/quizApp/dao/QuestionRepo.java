package com.example.quizApp.dao;

import com.example.quizApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);
/*
    @Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT :numberOfQuestions ",nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numberOfQuestions);*/

    @Query(value = "SELECT * FROM question q WHERE q.category = :category AND q.difficulty_level = :difficulty ORDER BY RAND() LIMIT :numberOfQuestions", nativeQuery = true)
    List<Question> findQuestionsByCategoryAndDifficulty(@Param("category") String category, @Param("difficulty") String difficulty, @Param("numberOfQuestions") int numberOfQuestions);


    @Query("SELECT DISTINCT q.category FROM Question q")
    List<String> findAllCategories();

    @Query(value = "SELECT q.right_answer FROM Question q WHERE q.id = :questionId",nativeQuery = true)
    String findRightAnswerByQuestionId(@Param("questionId") Integer questionId);

    @Query("SELECT DISTINCT q.difficultyLevel from Question q")
    List<String> findAllDifficultyLevels();



}
