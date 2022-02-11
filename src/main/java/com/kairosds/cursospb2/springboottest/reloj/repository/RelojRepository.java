package com.kairosds.cursospb2.springboottest.reloj.repository;

import com.kairosds.cursospb2.springboottest.reloj.Reloj;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelojRepository extends CrudRepository<Reloj, Long> {
}
