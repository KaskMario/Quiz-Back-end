package com.example.quizApp.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
public class Quiz {

    private String category;
    private List<Question> questions;



}
