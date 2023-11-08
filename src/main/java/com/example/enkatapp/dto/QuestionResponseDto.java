package com.example.enkatapp.dto;

import java.util.List;

public class QuestionResponseDto {

    private String text;
    private List<String> answers;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
