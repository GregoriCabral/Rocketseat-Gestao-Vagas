package br.com.gregori.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.gregori.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.gregori.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.gregori.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.gregori.gestao_vagas.modules.utils.TesteUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }
    
    @Test
    public void should_be_able_to_create_a_new_job() throws Exception {
        var company = new CompanyEntity();
        company.setDescription("COMPANY DESCRIPTION");
        company.setEmail("email@company.com");
        company.setName("COMPANY_NAME");
        company.setUsername("COMPANY_USERNAME");
        company.setPassword("12340");

        company = companyRepository.saveAndFlush(company);
         
        var createJobDTO = new CreateJobDTO();
        createJobDTO.setDescription("DESCRIPTION_TEST");
        createJobDTO.setBenefits("BENEFICIOS_TEST");
        createJobDTO.setLevel("LEVEL_TEST");
                        
        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TesteUtils.objectToJSON(createJobDTO))
                            .header("Authorization", TesteUtils.generateToken(company.getId(), "JAVAGAS_@123#")))
                            .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println(result);
    }

    @Test
    public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception {
            
        var createJobDTO = new CreateJobDTO();
        createJobDTO.setDescription("DESCRIPTION_TEST");
        createJobDTO.setBenefits("BENEFICIOS_TEST");
        createJobDTO.setLevel("LEVEL_TEST");
            
        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TesteUtils.objectToJSON(createJobDTO))
                            .header("Authorization", TesteUtils.generateToken(UUID.randomUUID(), "JAVAGAS_@123#")))
                            .andExpect(MockMvcResultMatchers.status().isBadRequest());                 

    }
}
