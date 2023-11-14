package com.example.enkatapp.services;

import com.example.enkatapp.models.Survey;
import com.example.enkatapp.models.UserEntity;
import com.example.enkatapp.repositories.SurveyRepository;
import com.example.enkatapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final ResponseService responseService;
    private final UserRepository userRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository, ResponseService responseService, UserRepository userRepository) {
        this.surveyRepository = surveyRepository;
        this.responseService = responseService;
        this.userRepository = userRepository;
    }
    public Survey createSurvey(Survey survey) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new NoSuchElementException("Användare hittades inte: " + currentUserName));

        currentUser.addSurvey(survey); // Lägger till survey till användaren och sätter användaren på survey
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

    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ingen survey hittades med ID: " + id));
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }
}
