package com.example.trip.entity;

import java.io.Serializable;
import java.util.Random;

public class Question implements Serializable, Comparable<Question> {
    private String id;
    private String question;
    private int order;
    private String correctAnswer;
    private String option1;
    private String option2;
    private String answer;

    public Question(){}

    public String getAnswer() {
        return answer;
    }

    public Question setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public String getId() {
        return id;
    }

    public Question setId(String id) {
        this.id = id;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public Question setQuestion(String question) {
        this.question = question;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public Question setOrder(int order) {
        this.order = order;
        return this;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Question setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
        return this;
    }

    public String getOption1() {
        return option1;
    }

    public Question setOption1(String option1) {
        this.option1 = option1;
        return this;
    }

    public String getOption2() {
        return option2;
    }

    public Question setOption2(String option2) {
        this.option2 = option2;
        return this;
    }

    @Override
    public int compareTo(Question question) {
        return this.order - question.order;
    }

    public String[] generateOptions(){
        String[] options = {this.correctAnswer, this.option1, this.option2};
        Random rnd = new Random();
        for (int i = options.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Swap array[i] and array[index]
            String temp = options[i];
            options[i] = options[index];
            options[index] = temp;
        }
        return options;
    }
}
