package com.example.enkatapp.services;

import com.example.enkatapp.DTO.AnswerDto;
import com.example.enkatapp.models.Answer;
import com.example.enkatapp.models.Question;
import com.example.enkatapp.models.Response;
import com.example.enkatapp.models.Survey;
import com.example.enkatapp.repositories.AnswerRepository;
import com.example.enkatapp.repositories.QuestionRepository;
import com.example.enkatapp.repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.example.enkatapp.repositories.SurveyRepository;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository, SurveyRepository surveyRepository,
                           QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.responseRepository = responseRepository;
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Response createResponse(Long surveyId, List<AnswerDto> answerDtos) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new NoSuchElementException("Ingen survey hittades med ID: " + surveyId));
        Response response = new Response();
        response.setSurvey(survey);

        // Spara response-objektet först så att det får ett ID
        response = responseRepository.save(response);

        List<Answer> answers = new ArrayList<>();
        for (AnswerDto answerDto : answerDtos) {
            if (answerDto.getQuestionId() == null) {
                throw new IllegalArgumentException("Fråge-ID i svaret får inte vara null");
            }
            Question question = questionRepository.findById(answerDto.getQuestionId())
                    .orElseThrow(() -> new NoSuchElementException("Ingen fråga hittades med ID: " + answerDto.getQuestionId()));

            Answer answer = new Answer();
            answer.setQuestion(question);
            answer.setText(answerDto.getText());
            answer.setSelectedOption(answerDto.getSelectedOption());
            answer.setScale(answerDto.getScale());

            // Sätt response på varje svar
            answer.setResponse(response);

            answers.add(answer);
            answerRepository.save(answer);
        }

        // Uppdatera response-objektet med listan av svar
        response.setAnswers(answers);

        // Spara response-objektet igen för att uppdatera relationen
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
