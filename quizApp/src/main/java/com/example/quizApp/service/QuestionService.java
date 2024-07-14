package com.example.quizApp.service;

import com.example.quizApp.dao.QuestionRepo;
import com.example.quizApp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionService {


    private final QuestionRepo questionRepo;

    @Autowired
    public QuestionService(QuestionRepo questionRepo){
        this.questionRepo=questionRepo;
    }

    public List<Question> getAllQuestions(){
        return questionRepo.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionRepo.findByCategory(category);
    }

    public Question addQuestion(Question question) {
        return questionRepo.save(question);
    }

    public void deleteQuestionById(Integer id) {
               questionRepo.deleteById(id);
    }
    public Question updateQuestion(Question question) {
        return questionRepo.save(question);
    }

    public List<Question> getUnapprovedQuestions() {
        return questionRepo.findByApprovedFalse();
    }

}
