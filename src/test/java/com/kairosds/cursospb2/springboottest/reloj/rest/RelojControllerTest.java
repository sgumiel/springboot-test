package com.kairosds.cursospb2.springboottest.reloj.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kairosds.cursospb2.springboottest.reloj.Reloj;
import com.kairosds.cursospb2.springboottest.reloj.repository.RelojRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RelojController.class)
public class RelojControllerTest {

    private static final String RELOJ_PATH = "/reloj";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RelojRepository relojRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGet() throws Exception {

        final var result = mockMvc.perform(get(RELOJ_PATH));

        final var relojes = this.objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                new TypeReference<List<Reloj>>() {}
        );

        Assertions.assertThat(relojes).isEmpty();
    }

    @Test
    void testPost() throws Exception {

        final var relojRequest = Reloj.builder().marca("Viceroy").modelo("V2022N").digital(false).build();
        final var relojReturnedByRepository = Reloj.builder().id(5L).marca("Viceroy").modelo("V2022N").digital(false).build();

        when(this.relojRepository.save(any(Reloj.class))).thenReturn(relojReturnedByRepository);

        final var result = mockMvc.perform(post(RELOJ_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(relojRequest))
        );

        final var relojCreado = this.objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                new TypeReference<Reloj>() {}
        );

        Assertions.assertThat(relojCreado).isNotNull();
        Assertions.assertThat(relojCreado.getId()).isEqualTo(5);
    }
}