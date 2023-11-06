package com.example.enkatapp.DTO;

import java.util.List;

public class ResponseRequestDto {
    private Long surveyId;
    private List<AnswerDto> answers;

    // getters och setters

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }
}



