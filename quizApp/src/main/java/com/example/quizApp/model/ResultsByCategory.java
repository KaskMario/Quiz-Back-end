package com.example.quizApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultsByCategory {
    String category;
    int quizzesTotal;
    int questionsTotal;
    double score;
}
