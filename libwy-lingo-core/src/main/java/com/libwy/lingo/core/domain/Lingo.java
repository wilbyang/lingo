package com.libwy.lingo.core.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;



import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

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
    public Lingo(final String teaser, final String content, final Lan lang, final User author) {
        this.teaser = teaser;
        this.content = content;
        this.lang = lang;
        this.author = author;
    }

}
