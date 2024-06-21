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
        List<Question> questions =  questionService.getAllQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity <List<Question>> getQuestionByCategory(@PathVariable String category){
        List<Question> questions =  questionService.getQuestionsByCategory(category);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question){
       Question newQuestion = questionService.addQuestion(question);
       return new ResponseEntity<>(newQuestion,HttpStatus.CREATED);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("id") Question question){
       questionService.deleteQuestion(question);
       return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity <Question> updateQuestion(@RequestBody Question question){
       Question updatedQuestion = questionService.updateQuestion(question);
        return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }



}
