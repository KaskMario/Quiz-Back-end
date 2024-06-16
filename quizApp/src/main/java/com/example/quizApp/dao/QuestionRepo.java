package com.example.quizApp.dao;

import com.example.quizApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    //void deleteQuestion(Integer id);
}
