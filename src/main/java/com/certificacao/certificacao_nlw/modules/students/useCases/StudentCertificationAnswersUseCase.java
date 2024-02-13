package com.certificacao.certificacao_nlw.modules.students.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certificacao.certificacao_nlw.modules.questions.entities.QuestionEntity;
import com.certificacao.certificacao_nlw.modules.questions.repositories.QuestionRepository;
import com.certificacao.certificacao_nlw.modules.students.dto.StudentCertificationAnswerDTO;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private QuestionRepository questionRepository;

    public StudentCertificationAnswerDTO execute(StudentCertificationAnswerDTO dto) {
        List<QuestionEntity> questionEntity = questionRepository.findByTechnology(dto.getTechnology());

        dto.getQuestionAnswers().stream().forEach(questionAnswer -> {
            var question = questionEntity.stream().filter(q -> q.getId().equals(questionAnswer.getQuestionID())).findFirst().get();

            var findCorrectAlternative = question.getAlternatives().stream().filter(alternative -> alternative.isCorrect()).findFirst().get();

            if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                questionAnswer.setCorrect(true);
            } else {
                questionAnswer.setCorrect(false);
            }
        });

        return dto;
        // Salvar as informações da certificação
    }

}
