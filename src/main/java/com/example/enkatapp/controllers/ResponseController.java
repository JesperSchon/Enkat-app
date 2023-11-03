package com.example.enkatapp.controllers;

import com.example.enkatapp.DTO.AnswerDTO;
import com.example.enkatapp.DTO.ResponseRequestDto;
import com.example.enkatapp.models.Answer;
import com.example.enkatapp.models.Question;
import com.example.enkatapp.models.Response;
import com.example.enkatapp.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/responses")
@CrossOrigin(origins = "http://localhost:3000")
public class ResponseController {

    private final ResponseService responseService;

    @Autowired
    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping
    public ResponseEntity<Response> createResponse(@RequestBody ResponseRequestDto responseRequest) {
        List<AnswerDTO> answerDtos = responseRequest.getAnswers();
        for (AnswerDTO answerDto : answerDtos) {
            if (answerDto.getQuestionId() == null) {
                throw new IllegalArgumentException("Fråge-ID i svaret får inte vara null");
            }
        }
        Response createdResponse = responseService.createResponse(responseRequest.getSurveyId(), answerDtos);
        return ResponseEntity.ok(createdResponse);
    }


    @GetMapping
    public ResponseEntity<List<Response>> getAllResponses() {
        List<Response> responses = responseService.getAllResponses();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getResponseById(@PathVariable Long id) {
        try {
            Response response = responseService.getResponseById(id);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

