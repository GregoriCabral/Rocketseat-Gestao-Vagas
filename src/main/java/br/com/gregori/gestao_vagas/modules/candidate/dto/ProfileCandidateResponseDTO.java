package br.com.gregori.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProfileCandidateResponseDTO {
    
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String description;

    public ProfileCandidateResponseDTO() { }

    public ProfileCandidateResponseDTO(UUID id, String name, String username, String email, String description) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
