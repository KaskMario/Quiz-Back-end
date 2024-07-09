package com.example.quizApp.service;

import com.example.quizApp.dao.QuestionRepo;
import com.example.quizApp.dao.StatsRepo;
import com.example.quizApp.dao.UserRepo;
import com.example.quizApp.model.QuizResult;
import com.example.quizApp.model.Statistics;
import com.example.quizApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class StatsService {

    @Autowired
    StatsRepo statsRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    QuestionRepo questionRepo;

    public QuizResult logQuiz (QuizResult archivedQuiz, String username){

        if(Objects.equals(archivedQuiz.getLength(), archivedQuiz.getCorrectAnswers())){
        archivedQuiz.setFlawless(true);}

        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        archivedQuiz.setUser(user);
        return statsRepo.save(archivedQuiz);


            }


    public Map<String, Integer> findCategoryCountsByUserId(int userId) {
        List<Object[]> favsFromRepo = statsRepo.findCategoryCountsByUserId(userId);
        Map<String, Integer> categoryCounts = new HashMap<>();

        for (Object[] row : favsFromRepo) {
            String category = (String) row[0];
            Integer count = Math.toIntExact(((Number) row[1]).longValue());
            categoryCounts.put(category, count);
        }

        return categoryCounts;
    }



    public String getFavouriteCategory (int userId){
        Map<String, Integer> categoryCounts = findCategoryCountsByUserId(userId);
        String maxCategory = null;
        int maxValue = 0;

        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxCategory = entry.getKey();
            }else if(entry.getValue()== maxValue && maxValue != 0){
                maxCategory = maxCategory + ", " + entry.getValue();
            }
            }
        if (maxCategory == null) {
            System.out.println("Map is empty");
        }
        return maxCategory;
    }

    public Map<Integer, Integer> findLengthCountsByUserId(int userId) {
        List<Object[]> lengthsFromRepo = statsRepo.findLengthCountsByUserId(userId);
        Map<Integer, Integer> lengthCounts = new HashMap<>();

        for (Object[] row : lengthsFromRepo ){
            Number lengthLong = (Number) row[0];
            Number countLong = (Number) row[1];
            Integer length = lengthLong.intValue();
            Integer count = countLong.intValue();
            lengthCounts.put(length, count);
        }

        return lengthCounts;
    }

    public String getPreferredLength(int userId){
        Map<Integer, Integer> lengthCounts = findLengthCountsByUserId(userId);
        String maxLength = null;
        int maxValue = 0;
        for (Map.Entry<Integer, Integer> entry : lengthCounts.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxLength = entry.getKey().toString();
            }else if(entry.getValue()== maxValue && maxValue != 0){
                maxLength = maxLength + " and " + entry.getValue();
            }
        }
        if (maxLength == null) {
            System.out.println("Map is empty");
        }
        return maxLength;

    }

    /*public String getPreferredLength(int userId){
        int length10 = 10;
        int length20 = 20;
        if(statsRepo.countByLength(length10, userId) > statsRepo.countByLength(length20, userId)){
            return "by 10 questions";
        }else if(statsRepo.countByLength(length10, userId) == statsRepo.countByLength(length20, userId)){
            return "by 10 and 20 questions equally";
        }else{return "by 20 questions";}
    }*/

    public double getScores(String difficulty, int userId){
        if(statsRepo.sumUpRightAnswers(difficulty, userId)!=null && statsRepo.sumUpQuestions(difficulty, userId) !=null) {
            double score = (double) statsRepo.sumUpRightAnswers(difficulty, userId) / statsRepo.sumUpQuestions(difficulty, userId) * 100;
            return Math.round(score * 100.0) / 100.0;
        }else{
            return 0;
        }
    }


    public List<String> getQuizDifficulties(){
        return questionRepo.findAllDifficultyLevels();
    }

    /*public List<String> getDifficulties(int userId){
        System.out.println(statsRepo.getUsersDifficulties(userId));
        return statsRepo.getUsersDifficulties(userId);

    }*/



    public Integer getFlawless (boolean isFlawless, int userId){
        return statsRepo.sumUpFlawless(isFlawless, userId);
    }



    public Statistics getStatsById(int userId){
       if(statsRepo.getPlayedUsers().contains(userId)){

            boolean isFlawless = true;
            int gamePlayed = statsRepo.countGamesByUserId(userId);

            String favouriteCategory = getFavouriteCategory(userId);
            String preferredLength = getPreferredLength(userId);

            List<String> difficultyLevels = getQuizDifficulties();
            double easyScore = getScores(difficultyLevels.get(0), userId);
            double mediumScore = getScores(difficultyLevels.get(1), userId);
            double hardScore = getScores(difficultyLevels.get(2), userId);

            int flawlessCount = getFlawless(isFlawless, userId);
            String lastTimePlayed = statsRepo.findMostRecentDate(userId);
            return new Statistics(gamePlayed, favouriteCategory, preferredLength, easyScore, mediumScore, hardScore, flawlessCount, lastTimePlayed);
        }else{
            return new Statistics();
        }
    }






}