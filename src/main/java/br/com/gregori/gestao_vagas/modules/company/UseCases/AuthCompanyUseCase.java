package br.com.gregori.gestao_vagas.modules.company.UseCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.gregori.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.gregori.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.gregori.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;
    
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Username/password incorrect.");
        });

        //Verificar a senha são igauis
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        // Se não for igual -> Erro
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // Se for igual -> Gerar o token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create().withIssuer("javagas")
                                .withExpiresAt(expiresIn)
                                .withSubject(company
                                .getId()
                                .toString())
                                .withClaim("roles", Arrays.asList("COMPANY"))
                                .sign(algorithm);

        var authCompanyResponseDTO = new AuthCompanyResponseDTO();
        authCompanyResponseDTO.setAccess_token(token);
        authCompanyResponseDTO.setExpires_in(expiresIn.toEpochMilli());

        return authCompanyResponseDTO;
    }
}
