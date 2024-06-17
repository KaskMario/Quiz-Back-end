package com.example.quizApp.service;

import com.example.quizApp.dao.QuestionRepo;
import com.example.quizApp.dao.QuizRepo;
import com.example.quizApp.model.Question;
import com.example.quizApp.model.QuestionWrapper;
import com.example.quizApp.model.Quiz;
import com.example.quizApp.model.QuizResponse;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepo quizRepo;
    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public ResponseEntity<String> createQuiz(String category, int numberOfQuestions, String title) {

        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category,numberOfQuestions);
        System.out.println("Number of questions fetched: " + questions.size());

        if (questions.isEmpty()) {
            return new ResponseEntity<>("No questions available for the specified category", HttpStatus.BAD_REQUEST);
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepo.save(quiz);

        entityManager.flush();

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

      Optional<Quiz> newQuiz = quizRepo.findById(id);
      List<Question> questionsFromDb = newQuiz.get().getQuestions();
      List<QuestionWrapper> questionsForUser = new ArrayList<>();
      for(Question question : questionsFromDb){
          QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(),
                  question.getQuestionTitle(),question.getOption1(),
                  question.getOption2(),question.getOption3(),question.getOption4());
          questionsForUser.add(questionWrapper);
      }

      return new ResponseEntity<>(questionsForUser,HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<QuizResponse> response) {
      Optional <Quiz> quiz = quizRepo.findById(id);
      List<Question> questions = quiz.get().getQuestions();
      int rightAnswer = 0;
      int i = 0;
      for(QuizResponse quizResponse : response){
          if(quizResponse.getResponse().equals(questions.get(i).getRightAnswer())){
              rightAnswer++;
          }
          i++;
      }
      return new ResponseEntity<>(rightAnswer,HttpStatus.OK);

    }
}
