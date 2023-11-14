package com.example.enkatapp.controllers;

import com.example.enkatapp.models.Survey;
import com.example.enkatapp.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/surveys")
@CrossOrigin(origins = "http://localhost:3000")
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public ResponseEntity<?> createSurvey(@RequestBody Survey survey) {
        try {
            Survey createdSurvey = surveyService.createSurvey(survey);
            return new ResponseEntity<>(createdSurvey, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error message related to the exception", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        try {
            List<Survey> surveys = surveyService.getAllSurveys();
            return ResponseEntity.ok(surveys);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable Long id) {
        try {
            Survey survey = surveyService.getSurveyById(id);
            return ResponseEntity.ok(survey);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id, @RequestBody Survey surveyDetails) {
        try {
            Survey updatedSurvey = surveyService.updateSurvey(id, surveyDetails);
            return ResponseEntity.ok(updatedSurvey);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        try {
            surveyService.deleteSurvey(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

