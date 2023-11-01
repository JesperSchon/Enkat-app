package com.example.enkatapp.DTO;

import java.util.List;

public class ResponseRequestDto {
    private Long surveyId;
    private List<AnswerDTO> answers;

    // getters och setters

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}



