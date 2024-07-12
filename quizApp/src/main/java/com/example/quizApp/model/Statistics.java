package com.example.quizApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Statistics {
    int gamePlayed;
    String favouriteCategory;
    String preferredLength;
    double easyScore;
    double mediumScore;
    double hardScore;
    int flawlessCount;
    String lastTimePlayed;


}
