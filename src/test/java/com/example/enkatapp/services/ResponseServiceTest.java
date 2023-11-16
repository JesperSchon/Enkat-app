package com.example.enkatapp.services;

import com.example.enkatapp.repositories.AnswerRepository;
import com.example.enkatapp.repositories.QuestionRepository;
import com.example.enkatapp.repositories.ResponseRepository;
import com.example.enkatapp.repositories.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ResponseServiceTest {

    @Mock
    private ResponseRepository responseRepository;
    @Mock
    private SurveyRepository surveyRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private ResponseService responseService;

    @BeforeEach
    void setUp() {}


    @Test
    void testCreateResponse() {

    }

    @Test
    void testGetResponseById() {

    }

    @Test
    void testGetAllResponses() {

    }

    @Test
    void testDeleteResponse() {

    }

    @Test
    void testGetResponsesBySurveyId() {

    }





    

}
