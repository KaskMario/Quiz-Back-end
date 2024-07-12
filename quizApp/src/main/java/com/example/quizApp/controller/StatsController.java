package com.example.quizApp.controller;

import com.example.quizApp.model.QuizResult;
import com.example.quizApp.model.ResultsByCategory;
import com.example.quizApp.model.Statistics;
import com.example.quizApp.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    StatsService statsService;

    @PostMapping ("/log/{username}")
    public ResponseEntity<QuizResult> logQuiz (@RequestBody QuizResult archivedQuiz, @PathVariable String username) {
        statsService.logQuiz(archivedQuiz, username);
        QuizResult newQuiz = statsService.logQuiz(archivedQuiz, username);
        return new ResponseEntity<>(newQuiz, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Statistics> getStats(@PathVariable int userId){
        Statistics stats = statsService.getStatsById(userId);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @GetMapping("/bycategory/{userId}")
    public ResponseEntity<List<ResultsByCategory>> getResultsByCategory(@PathVariable int userId){
        List<ResultsByCategory> listOfResultsByCategory = statsService.getResultsByCategory(userId);
        return new ResponseEntity<>(listOfResultsByCategory, HttpStatus.OK);
    }


}
