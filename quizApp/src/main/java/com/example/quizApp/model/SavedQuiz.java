package com.example.quizApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;




@Entity
@AllArgsConstructor
@Table(name = "saved_quizzes")
public class SavedQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String questions;
    private String description;

    @ManyToOne
    @JoinColumn(name = "quiz_result_id")
    private QuizResult quizResult;
}
