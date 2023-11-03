package com.example.enkatapp.controllers;

import com.example.enkatapp.models.Question;
import com.example.enkatapp.models.Survey;
import com.example.enkatapp.services.QuestionService;
import com.example.enkatapp.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionController {

    private final QuestionService questionService;
    private final SurveyService surveyService;

    @Autowired
    public QuestionController(QuestionService questionService, SurveyService surveyService) {
        this.questionService = questionService;
        this.surveyService = surveyService;
    }


    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody Question question, @RequestParam(required = false) Long surveyId) {
        if (surveyId == null) {
            return ResponseEntity.badRequest().body("Survey ID must be provided");
        }

        Survey survey;
        try {
            survey = surveyService.getSurveyById(surveyId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Survey not found with id " + surveyId);
        }

        question.setSurvey(survey);
        Question createdQuestion = questionService.createQuestion(question);
        survey.getQuestions().add(createdQuestion);
        surveyService.updateSurvey(surveyId, survey);

        return ResponseEntity.ok(createdQuestion);
    }


    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        try {
            Question question = questionService.getQuestionById(id);
            return ResponseEntity.ok(question);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question questionDetails) {
        try {
            Question updatedQuestion = questionService.updateQuestion(id, questionDetails);
            return ResponseEntity.ok(updatedQuestion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
