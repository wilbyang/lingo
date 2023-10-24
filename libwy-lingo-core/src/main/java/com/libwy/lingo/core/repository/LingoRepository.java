package com.libwy.lingo.core.repository;

import com.libwy.lingo.core.domain.Lingo;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface LingoRepository extends Repository<Lingo, Long> {
    Lingo save(Lingo lingo);
    Iterable<Lingo> findAll();
    Optional<Lingo> findById(Long id);
    Iterable<Lingo> findBySourceIsNull();
}
