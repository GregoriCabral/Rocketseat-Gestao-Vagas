package br.com.gregori.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gregori.gestao_vagas.exception.JobNotFoundException;
import br.com.gregori.gestao_vagas.exception.UserNotFoundException;
import br.com.gregori.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.gregori.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.gregori.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.gregori.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRespository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;
    
    // ID do candidato
    // ID da vaga
    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        // Validar se o candidato existe
        this.candidateRespository.findById(idCandidate).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        // Validar se a vaga existe
        this.jobRepository.findById(idJob).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        // Candidato se inscrever na vaga
        var applyJob = new ApplyJobEntity();
        applyJob.setCandidateId(idCandidate);
        applyJob.setJobId(idJob);

        System.out.println(applyJob);

        applyJob = applyJobRepository.save(applyJob);
        return applyJob;
    }
}
