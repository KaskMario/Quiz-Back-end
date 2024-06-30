package com.example.quizApp.controller;

import com.example.quizApp.model.Question;
import com.example.quizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<Map<String, String>> deleteQuestion(@PathVariable Integer id) {
        boolean isDeleted = questionService.deleteQuestionById(id);
        Map<String, String> response = new HashMap<>();
        if (isDeleted) {
            response.put("message", "Question deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Question not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update")
    public ResponseEntity <Question> updateQuestion(@RequestBody Question question){
       Question updatedQuestion = questionService.updateQuestion(question);
        return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }

}
