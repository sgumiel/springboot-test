package com.kairosds.cursospb2.springboottest.reloj.repository;

import com.kairosds.cursospb2.springboottest.reloj.Reloj;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class RelojRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RelojRepository repository;

    @Test
    public void saveReloj() {
        var reloj = Reloj.builder().marca("Casio").modelo("CS-109B").digital(true).build();
        entityManager.persistAndFlush(reloj);

        Assertions.assertThat(reloj.getId()).isNotNull();
    }

    @Test
    public void repositoryEmpty() {

        Assertions.assertThat(repository.findAll()).isEmpty();
    }
}