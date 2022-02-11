package com.kairosds.cursospb2.springboottest.reloj.rest;

import java.util.List;

import com.kairosds.cursospb2.springboottest.reloj.Reloj;
import com.kairosds.cursospb2.springboottest.reloj.repository.RelojRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("reloj")
public class RelojController {

    private final RelojRepository relojRepository;

    @GetMapping
    public ResponseEntity<List<Reloj>> findAll() {

        final var relojes = (List) this.relojRepository.findAll();
        return ResponseEntity.ok(relojes);
    }

    @PostMapping
    public ResponseEntity<Reloj> createReloj(@RequestBody Reloj reloj) {

        final var relojSaved = this.relojRepository.save(reloj);
        return ResponseEntity.ok(relojSaved);
    }
}