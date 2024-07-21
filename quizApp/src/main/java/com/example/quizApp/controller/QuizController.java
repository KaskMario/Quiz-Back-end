package com.example.quizApp.controller;

import com.example.quizApp.model.Question;
import com.example.quizApp.model.QuestionWrapper;
import com.example.quizApp.model.SavedQuiz;
import com.example.quizApp.model.User;
import com.example.quizApp.service.QuizService;
import com.example.quizApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @Autowired
    UserService userService;

    @GetMapping("/get")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(
            @RequestParam String category,
            @RequestParam String difficulty,
            @RequestParam int numberOfQuestions) {

        List<QuestionWrapper> questions = quizService.getQuizQuestions(category, difficulty, numberOfQuestions);

        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = quizService.getAllCategories();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @GetMapping("/difficulty-levels")
    public ResponseEntity<List<String>> getAllDifficultyLevels() {
        List<String> difficultyLevels = quizService.getAllDifficultyLevels();
        if (difficultyLevels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(difficultyLevels, HttpStatus.OK);
    }

    @GetMapping("/answer/{questionId}")
    public ResponseEntity<Map<String, String>> getRightAnswer(@PathVariable Integer questionId) {
        String rightAnswer = quizService.getRightAnswer(questionId);
        if (rightAnswer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<String, String> response = new HashMap<>();
        response.put("rightAnswer", rightAnswer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity <SavedQuiz> saveQuiz(@RequestParam int quizResultId, @RequestParam int userId, @RequestBody SavedQuiz savedQuiz){
        System.out.println(savedQuiz.toString());
        SavedQuiz newSavedQuiz = quizService.saveQuiz(savedQuiz, quizResultId, userId);

        return new ResponseEntity<>(newSavedQuiz, HttpStatus.OK);
    }

    @GetMapping("saved/{userId}")
    public ResponseEntity<List<SavedQuiz>> getSavedQuizzes(@PathVariable int userId){
        List<SavedQuiz> listOfQuizzes = quizService.getAllSaved(userId);
        return new ResponseEntity<>(listOfQuizzes, HttpStatus.OK);
    }

    @GetMapping("saved_questions")
    public ResponseEntity<List<QuestionWrapper>> getSavedQuizzes(@RequestParam String quizQuestions){
        List<QuestionWrapper> questions = quizService.getSavedQuestions(quizQuestions);

        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @DeleteMapping("/saves/delete/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Integer id) {
        quizService.deleteSavedQuiz(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }




}
