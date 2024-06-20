package com.example.quizApp.model;

import lombok.Data;

import java.util.List;

@Data
public class QuestionWrapper {

    private Integer id;
    private String questionTitle;
    private List<String> options;

    public QuestionWrapper(Integer id, String questionTitle, List<String>options) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.options = options;
    }
}
