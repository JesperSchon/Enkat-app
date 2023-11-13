package com.example.enkatapp.dto;

import java.util.List;

public class SurveyResponseDto {

    private String title;
    private List<QuestionResponseDto> questions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionResponseDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResponseDto> questions) {
        this.questions = questions;
    }
}
