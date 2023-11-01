package com.example.enkatapp.services;

import com.example.enkatapp.models.Question;
import com.example.enkatapp.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ingen fråga hittades med ID: " + id));
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question updateQuestion(Long id, Question questionDetails) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fråga inte hittad med id " + id));
        question.setText(questionDetails.getText());
        question.setType(questionDetails.getType());
        question.setOptions(questionDetails.getOptions());
        question.setMinScale(questionDetails.getMinScale());
        question.setMaxScale(questionDetails.getMaxScale());
        return questionRepository.save(question);
    }
}


