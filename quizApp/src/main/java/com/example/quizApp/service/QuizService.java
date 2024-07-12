package com.example.quizApp.service;

import com.example.quizApp.dao.QuestionRepo;
import com.example.quizApp.dao.QuizSaveRepo;
import com.example.quizApp.model.Question;
import com.example.quizApp.model.QuestionWrapper;
import com.example.quizApp.model.SavedQuiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private QuizSaveRepo quizSaveRepo;

    public List<QuestionWrapper> getQuizQuestions(String category,String difficulty, int numberOfQuestions) {
        List<Question> questionsFromDb = questionRepo.findQuestionsByCategoryAndDifficulty(category,difficulty,numberOfQuestions);
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

    public List<String> getAllDifficultyLevels() {
        return questionRepo.findAllDifficultyLevels();
        /*List<String> difficultyLevels = questionRepo.findAllDifficultyLevels();
        System.out.println("Difficulty Levels: " + difficultyLevels);
        return difficultyLevels;*/
    }

    public SavedQuiz saveQuiz(SavedQuiz savedQuiz) {
        return quizSaveRepo.save(savedQuiz);}


}
