package com.example.enkatapp.DTO;

import com.example.enkatapp.models.Answer;

import java.util.List;

public class ResponseRequestDto {
    private Long surveyId;
    private List<Answer> answers;

    // getters och setters

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
