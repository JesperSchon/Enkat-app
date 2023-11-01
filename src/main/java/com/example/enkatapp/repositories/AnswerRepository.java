package com.example.enkatapp.repositories;

import com.example.enkatapp.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
