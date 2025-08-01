package br.com.gregori.gestao_vagas.exception;

import lombok.Data;

@Data
public class ErrorMessageDTO {
    private String message;
    private String field;

    public ErrorMessageDTO() {}

    public ErrorMessageDTO(String message, String field) {
        this.message = message;
        this.field = field;
    }

}
