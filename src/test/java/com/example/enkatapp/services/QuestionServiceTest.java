package com.example.enkatapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.enkatapp.models.Question;
import com.example.enkatapp.models.QuestionType;
import com.example.enkatapp.repositories.QuestionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    public void setUp() {
        // Any setup needed before each test
    }

    @Test
    public void testCreateQuestion() {
        Question question = new Question();

        when(questionRepository.save(any(Question.class))).thenReturn(question);

        Question createdQuestion = questionService.createQuestion(question);

        assertNotNull(createdQuestion);
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    public void testDeleteQuestion() {
        Long id = 1L;
        doNothing().when(questionRepository).deleteById(id);
        questionService.deleteQuestion(id);
        verify(questionRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetQuestionById() {
        Long id = 1L;

        Question question = new Question();

        when(questionRepository.findById(id)).thenReturn(Optional.of(question));

        Question result = questionService.getQuestionById(id);

        assertEquals(question, result);
    }

    @Test
    public void testGetAllQuestions() {
        List<Question> questions = Collections.singletonList(new Question());

        when(questionRepository.findAll()).thenReturn(questions);

        List<Question> result = questionService.getAllQuestions();

        assertFalse(result.isEmpty());
        assertEquals(questions, result);
    }

    @Test
    public void testUpdateQuestion() {
        Long id = 1L;

        Question existingQuestion = new Question();

        Question updatedDetails = new Question();

        updatedDetails.setText("New Text");
        updatedDetails.setType(QuestionType.valueOf("TEXT"));


        when(questionRepository.findById(id)).thenReturn(Optional.of(existingQuestion));
        when(questionRepository.save(any(Question.class))).thenReturn(updatedDetails);

        Question updatedQuestion = questionService.updateQuestion(id, updatedDetails);

        assertNotNull(updatedQuestion);
        verify(questionRepository, times(1)).findById(id);
        verify(questionRepository, times(1)).save(any(Question.class));
    }
}
