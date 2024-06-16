package com.example.quizApp.controller;

import com.example.quizApp.model.Question;
import com.example.quizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }
    @GetMapping("/category/{category}")
    public List<Question> getQuestionByCategory(@PathVariable String category){
       return questionService.getQuestionsByCategory(category);
    }
    @PostMapping("/add")
    public Question addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);

    }
    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable("id") Question question){
       questionService.deleteQuestion(question);
    }
    @PutMapping("/update")
    public Question updateQuestion(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }



}
