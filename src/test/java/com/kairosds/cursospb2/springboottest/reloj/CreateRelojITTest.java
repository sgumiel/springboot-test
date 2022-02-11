package com.kairosds.cursospb2.springboottest.reloj;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kairosds.cursospb2.springboottest.reloj.Reloj;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class CreateRelojITTest {

    private static final String RELOJ_PATH = "/reloj";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreateReloj() throws Exception {

        final var relojRequest = Reloj.builder().marca("Viceroy").modelo("V2022N").digital(false).build();


        final var result = mockMvc.perform(post(RELOJ_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(relojRequest))
        );

        final var relojCreado = this.objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                new TypeReference<Reloj>() {}
        );

        Assertions.assertThat(relojCreado).isNotNull();
        Assertions.assertThat(relojCreado.getId()).isEqualTo(1);
    }

    @Test
    void testFindAll() throws Exception {

        this.entityManager.persist(Reloj.builder().marca("Viceroy").modelo("V2022N").digital(false).build());
        this.entityManager.persist(Reloj.builder().marca("Rolex").modelo("RL768").digital(false).build());

        final var result = mockMvc.perform(get(RELOJ_PATH));

        final var relojesObtenidos = this.objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                new TypeReference<List<Reloj>>() {}
        );

        Assertions.assertThat(relojesObtenidos).isNotEmpty();
        Assertions.assertThat(relojesObtenidos.size()).isEqualTo(2);
    }

}