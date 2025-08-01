package br.com.gregori.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gregori.gestao_vagas.exception.UserNotFoundException;
import br.com.gregori.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.gregori.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute (UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        var candidateDTO = new ProfileCandidateResponseDTO();
        candidateDTO.setId(candidate.getId());
        candidateDTO.setName(candidate.getName());
        candidateDTO.setUsername(candidate.getUsername());
        candidateDTO.setEmail(candidate.getEmail());
        candidateDTO.setDescription(candidate.getDescription());

        return candidateDTO;
    }
}
