package br.com.gregori.gestao_vagas.exception;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super("User not found");
    }
    
}
