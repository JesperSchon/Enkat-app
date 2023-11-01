package com.example.enkatapp.services;

import com.example.enkatapp.models.Answer;
import com.example.enkatapp.models.Response;
import com.example.enkatapp.models.Survey;
import com.example.enkatapp.repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final SurveyService surveyService;

    @Autowired
    public ResponseService(ResponseRepository responseRepository, SurveyService surveyService) {
        this.responseRepository = responseRepository;
        this.surveyService = surveyService;
    }

    public Response createResponse(Long surveyId, List<Answer> answers) {
        Survey survey = surveyService.getSurveyById(surveyId);
        Response response = new Response();
        response.setSurvey(survey);
        response.setAnswers(answers);
        return responseRepository.save(response);
    }


    public void deleteResponse(Long id) {
        responseRepository.deleteById(id);
    }

    public Response getResponseById(Long id) {
        return responseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Inget svar hittades med ID: " + id));
    }

    public List<Response> getAllResponses() {
        return responseRepository.findAll();
    }

    public Response updateResponse(Long id, Response responseDetails) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Svar inte hittat med id " + id));
        response.setSurvey(responseDetails.getSurvey());
        response.setAnswers(responseDetails.getAnswers());
        return responseRepository.save(response);
    }
}
