package com.example.enkatapp.services;

import com.example.enkatapp.models.Answer;
import com.example.enkatapp.models.Question;
import com.example.enkatapp.models.Response;
import com.example.enkatapp.models.Survey;
import com.example.enkatapp.repositories.AnswerRepository;
import com.example.enkatapp.repositories.QuestionRepository;
import com.example.enkatapp.repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Response createResponse(Long surveyId, List<Answer> answers) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new NoSuchElementException("Ingen survey hittades med ID: " + surveyId));
        Response response = new Response();
        response.setSurvey(survey);

        for (Answer answer : answers) {
            if (answer.getQuestion() == null || answer.getQuestion().getId() == null) {
                throw new IllegalArgumentException("Frågan i svaret får inte vara null");
            }
            Question question = questionRepository.findById(answer.getQuestion().getId())
                    .orElseThrow(() -> new NoSuchElementException("Ingen fråga hittades med ID: " + answer.getQuestion().getId()));
            answer.setQuestion(question);
            answerRepository.save(answer);
        }

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
