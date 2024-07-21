package com.example.quizApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "saved_quizzes")
public class SavedQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //private String questions;
    private String description;

    @OneToOne
    @JoinColumn(name = "quiz_result_id")
    private QuizResult quizResult;

    @ManyToMany
    @JoinTable(
            name = "savedquiz_questions",
            joinColumns = @JoinColumn(name = "savedquiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> questions;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
