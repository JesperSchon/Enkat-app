package com.example.enkatapp.services;

import com.example.enkatapp.DTO.QuestionResponseDto;
import com.example.enkatapp.DTO.SurveyResponseDto;
import com.example.enkatapp.models.Answer;
import com.example.enkatapp.models.Question;
import com.example.enkatapp.models.Response;
import com.example.enkatapp.models.Survey;
import com.example.enkatapp.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final ResponseService responseService;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository, ResponseService responseService) {
        this.surveyRepository = surveyRepository;
        this.responseService = responseService;
    }
    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }

    public Survey updateSurvey(Long id, Survey surveyDetails) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with id " + id));
        survey.setTitle(surveyDetails.getTitle());
        survey.setDescription(surveyDetails.getDescription());
        survey.setQuestions(surveyDetails.getQuestions());
        return surveyRepository.save(survey);
    }

    public SurveyResponseDto getSurveyWithResponses(Long surveyId) {
        Survey survey = getSurveyById(surveyId);
        List<QuestionResponseDto> questionResponses = new ArrayList<>();
        for (Question question : survey.getQuestions()) {
            List<String> answers = new ArrayList<>();
            List<Response> responses = responseService.getAllResponses();
            for (Response response : responses) {
                for (Answer answer : response.getAnswers()) {
                    if (answer.getQuestion().getId().equals(question.getId())) {
                        answers.add(answer.getText());
                    }
                }
            }
            QuestionResponseDto questionResponse = new QuestionResponseDto();
            questionResponse.setText(question.getText());
            questionResponse.setAnswers(answers);
            questionResponses.add(questionResponse);
        }
        SurveyResponseDto surveyResponse = new SurveyResponseDto();
        surveyResponse.setTitle(survey.getTitle());
        surveyResponse.setQuestions(questionResponses);
        return surveyResponse;
    }


    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ingen survey hittades med ID: " + id));
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }
}
