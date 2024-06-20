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
            List<String> options = new ArrayList<>();
            options.add(question.getOption1());
            options.add(question.getOption2());
            options.add(question.getOption3());
            options.add(question.getOption4());
            Collections.shuffle(options);

            QuestionWrapper questionWrapper = new QuestionWrapper(
                    question.getId(),
                    question.getQuestionTitle(),
                    options
            );
            questionsForUser.add(questionWrapper);
        }

        return questionsForUser;
    }

    public List<String> getAllCategories() {
        return questionRepo.findAllCategories();
    }

    public String getRightAnswer(Integer questionId) {
        return questionRepo.findRightAnswerByQuestionId(questionId);
    }

}
