package com.example.trip.utils;

import com.example.trip.entity.Question;

import java.util.ArrayList;

public class Exam {
    private ArrayList<Question> questions;
    private int currentQuestion;

    public Exam(ArrayList<Question> questions){
        this.questions = questions;
        this.currentQuestion = -1;
    }

    public Question nextQuestion(){
        this.currentQuestion++;
        if(this.currentQuestion >= this.questions.size()){
            return this.questions.get(this.questions.size() -1);
        }
        return this.questions.get(this.currentQuestion);
    }

    public boolean isComplete(){
        return this.currentQuestion >= this.questions.size() - 1;
    }
}
