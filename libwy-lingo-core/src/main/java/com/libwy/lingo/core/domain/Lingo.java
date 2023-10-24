package com.libwy.lingo.core.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@EqualsAndHashCode(of = {"teaser", "content", "lang"})
@NoArgsConstructor
@Getter
@Setter
public class Lingo {
    @Id
    private Long id;
    private String teaser;
    private String content;

    @ElementCollection
    @CollectionTable(name = "media_paths", joinColumns = @JoinColumn(name = "lingo_id"))
    @Column(name = "path")
    private List<String> mediaPaths;

    @ManyToOne
    private User author;

    @Enumerated(EnumType.STRING)
    private Lan lang;

    @ManyToOne
    private Lingo source;
    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lingo> translations;

    @Builder
    public Lingo(String teaser, String content, Lan lang, User author) {
        this.teaser = teaser;
        this.content = content;
        this.lang = lang;
        this.author = author;
    }

}
