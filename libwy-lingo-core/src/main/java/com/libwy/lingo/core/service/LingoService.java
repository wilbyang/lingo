package com.libwy.lingo.core.service;

import com.google.common.collect.Streams;
import com.libwy.lingo.core.domain.Lingo;
import com.libwy.lingo.core.repository.LingoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LingoService {

    private final LingoRepository lingoRepository;

    @Autowired
    public LingoService(LingoRepository lingoRepository) {
        this.lingoRepository = lingoRepository;
    }
    public List<Lingo> getAllRoots() {
        var lingos = lingoRepository.findBySourceIsNull();
        return Streams.stream(lingos).toList();
    }
    public Optional<Lingo> getById(Long id) {
        return lingoRepository.findById(id);

    }
    public Lingo saveLingo(Lingo lingo) {
        return lingoRepository.save(lingo);
    }
}
