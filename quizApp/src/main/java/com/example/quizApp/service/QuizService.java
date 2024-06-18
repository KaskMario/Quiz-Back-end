package com.example.quizApp.service;

import com.example.quizApp.dao.QuestionRepo;
import com.example.quizApp.model.Question;
import com.example.quizApp.model.QuestionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuestionRepo questionRepo;

    public List<QuestionWrapper> getQuizQuestions(String category, int numberOfQuestions) {
        List<Question> questionsFromDb = questionRepo.findRandomQuestionsByCategory(category, numberOfQuestions);
        List<QuestionWrapper> questionsForUser = new ArrayList<>();

        for (Question question : questionsFromDb) {
            QuestionWrapper questionWrapper = new QuestionWrapper(
                    question.getId(),
                    question.getQuestionTitle(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4()
            );
            questionsForUser.add(questionWrapper);
        }

        return questionsForUser;
    }

    public List<String> getAllCategories() {
        return questionRepo.findAllCategories();
    }

}
