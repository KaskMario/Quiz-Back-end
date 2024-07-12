package com.example.quizApp.dao;

import com.example.quizApp.model.SavedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSaveRepo extends JpaRepository<SavedQuiz, Integer> {
}
