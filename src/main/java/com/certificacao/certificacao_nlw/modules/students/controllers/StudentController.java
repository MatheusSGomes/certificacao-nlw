package com.certificacao.certificacao_nlw.modules.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certificacao.certificacao_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.certificacao.certificacao_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.certificacao.certificacao_nlw.modules.students.entities.CertificationStudentEntity;
import com.certificacao.certificacao_nlw.modules.students.useCases.StudentCertificationAnswersUseCase;
import com.certificacao.certificacao_nlw.modules.students.useCases.VerifyIfHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {

    // Use Case
    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
        var result = this.verifyIfHasCertificationUseCase.execute(verifyHasCertificationDTO);

        if (result) {
            return "Usuário já fez a prova";
        }

        return "Usuário pode fazer a prova";
    }

    @PostMapping("/certification/answer")
    public CertificationStudentEntity certificationAnswer(@RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) {
        return this.studentCertificationAnswersUseCase.execute(studentCertificationAnswerDTO);
    }
}
