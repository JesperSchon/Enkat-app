package com.example.enkatapp.services;

import com.example.enkatapp.models.Survey;
import com.example.enkatapp.models.UserEntity;
import com.example.enkatapp.repositories.SurveyRepository;
import com.example.enkatapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResponseService responseService;

    @InjectMocks
    private SurveyService surveyService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreateSurvey() {
        // Mock the current user
        UserEntity currentUser = new UserEntity();
        currentUser.setUsername("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(currentUser));

        // Mock Security Context
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken("testUser", null));

        // Create a survey
        Survey survey = new Survey(); // Set up your survey object
        when(surveyRepository.save(any(Survey.class))).thenReturn(survey);

        // Call the method to test
        Survey createdSurvey = surveyService.createSurvey(survey);

        // Assertions
        assertNotNull(createdSurvey);
        verify(userRepository, times(1)).findByUsername("testUser");
        verify(surveyRepository, times(1)).save(survey);
    }

    @Test
    public void testDeleteSurvey() {
        // Given
        Long surveyId = 1L; // Example survey ID

        // When
        surveyService.deleteSurvey(surveyId);

        // Then
        verify(surveyRepository, times(1)).deleteById(surveyId);
    }

    @Test
    public void testGetSurveyById() {
        Long id = 1L;
        Survey survey = new Survey();
        when(surveyRepository.findById(id)).thenReturn(Optional.of(survey));
        Survey result = surveyService.getSurveyById(id);
        assertEquals(survey, result);
    }

    @Test
    public void testUpdateSurvey() {
        Long id = 1L;
        Survey survey = new Survey();
        when(surveyRepository.findById(id)).thenReturn(Optional.of(survey));
        surveyService.updateSurvey(id, survey);
        verify(surveyRepository, times(1)).save(survey);
    }

    @Test
    public void testGetAllSurveys() {
        List<Survey> surveys = Collections.singletonList(new Survey());
        when(surveyRepository.findAll()).thenReturn(surveys);
        List<Survey> result = surveyService.getAllSurveys();
        assertFalse(result.isEmpty());
        assertEquals(surveys, result);
    }

    // Other test cases...
}
