package com.example.quizApp.service;

import com.example.quizApp.dao.QuestionRepo;
import com.example.quizApp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuestionService {


    private final QuestionRepo questionRepo;

    @Autowired
    public QuestionService(QuestionRepo questionRepo){
        this.questionRepo=questionRepo;
    }

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionRepo.findByCategory(category);
    }

    public Question addQuestion(Question question) {
        return questionRepo.save(question);
    }

    public void deleteQuestion(Question question) {
       questionRepo.delete(question);
    }

    public Question updateQuestion(Question question) {
        return questionRepo.save(question);
    }
}
