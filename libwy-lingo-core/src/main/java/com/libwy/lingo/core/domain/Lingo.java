package com.libwy.lingo.core.domain;


import jakarta.persistence.*;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Entity
@EqualsAndHashCode(of = {"teaser", "content", "lang"})
@NoArgsConstructor
@Getter
@Setter
public class Lingo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    @Column(name = "created_time", updatable = false)
    private Instant createdTime;
    @Builder
    public Lingo(final String teaser, final String content, final Lan lang, final User author) {
        this.teaser = teaser;
        this.content = content;
        this.lang = lang;
        this.author = author;
    }

}
