package com.example.quizApp.dao;

import com.example.quizApp.model.SavedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizSaveRepo extends JpaRepository<SavedQuiz, Integer> {

    List<SavedQuiz> findByUserId(Integer userId);
}
