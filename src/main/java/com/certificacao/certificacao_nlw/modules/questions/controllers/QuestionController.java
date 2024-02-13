package com.certificacao.certificacao_nlw.modules.questions.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certificacao.certificacao_nlw.modules.questions.dto.AlternativeResultDTO;
import com.certificacao.certificacao_nlw.modules.questions.dto.QuestionResultDTO;
import com.certificacao.certificacao_nlw.modules.questions.entities.AlternativesEntity;
import com.certificacao.certificacao_nlw.modules.questions.entities.QuestionEntity;
import com.certificacao.certificacao_nlw.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
        var result = this.questionRepository.findByTechnology(technology);

        var toMap = result.stream().map(question -> mapQuestionToDto(question))
            .collect(Collectors.toList());

        return toMap;
    }

    static QuestionResultDTO mapQuestionToDto(QuestionEntity question) {
        var questionResultDTO = QuestionResultDTO.builder()
            .id(question.getId())
            .technology(question.getTechnology())
            .description(question.getDescription()).build();

        List<AlternativeResultDTO> alternativeResultDTOs =
            question.getAlternatives().stream()
            .map(alternative -> mapAlternativeResultDTO(alternative))
            .collect(Collectors.toList());

        questionResultDTO.setAlternatives(alternativeResultDTOs);

        return questionResultDTO;
    }

    static AlternativeResultDTO mapAlternativeResultDTO(AlternativesEntity alternativesResultDTO) {
        return AlternativeResultDTO.builder()
            .id(alternativesResultDTO.getId())
            .description(alternativesResultDTO.getDescription()).build();
    }

}
