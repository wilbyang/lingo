package com.libwy.lingo.api.rest;

import com.libwy.lingo.core.domain.Lingo;
import com.libwy.lingo.core.service.LingoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class LingoResource {
    private LingoService lingoService;

    public LingoResource(LingoService lingoService) {
        this.lingoService = lingoService;
    }


    @GetMapping("/lingos")
    public List<Lingo> getLingos() {
        return this.lingoService.getAllRoots();
    }
    @GetMapping("/lingos/{id}")
    public Lingo getLingos(@PathVariable("id") Long id) {
        Optional<Lingo> optional = this.lingoService.getById(id);
        return optional.orElse(null);
    }
}
