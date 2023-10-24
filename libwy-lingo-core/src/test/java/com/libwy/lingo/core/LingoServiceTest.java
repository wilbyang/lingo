package com.libwy.lingo.core;

import com.libwy.lingo.core.domain.Lan;
import com.libwy.lingo.core.domain.Lingo;
import com.libwy.lingo.core.repository.LingoRepository;
import com.libwy.lingo.core.service.LingoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LingoServiceTest {
    private LingoService lingoService;
    private LingoRepository lingoRepository;

    @BeforeEach
    public void setUp() {
        this.lingoRepository = mock(LingoRepository.class);
        this.lingoService = new LingoService(this.lingoRepository);
    }
    @Test
    public void should_add_lingo() {
        when(lingoRepository.save(any())).then(returnsFirstArg());
        var lingo = Lingo.builder()
                .teaser("foo")
                .content("content")
                .lang(Lan.CHINESE)
                .build();
        Lingo saved = this.lingoService.saveLingo(lingo);
        assertThat(saved.getTeaser()).isEqualTo("foo");

    }

}
