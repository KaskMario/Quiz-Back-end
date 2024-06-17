package com.example.quizApp.dao;

import com.example.quizApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT :numberOfQuestions ",nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numberOfQuestions);


}
