package com.example.quizApp.controller;


import com.example.quizApp.model.Question;
import com.example.quizApp.model.QuestionWrapper;
import com.example.quizApp.model.QuizResponse;
import com.example.quizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numberOfQuestions, @RequestParam String title){
         return quizService.createQuiz(category,numberOfQuestions,title);

    }
    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);

    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<QuizResponse> response){
        return quizService.calculateResult(id,response);
    }


}
