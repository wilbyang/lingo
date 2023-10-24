package com.libwy.lingo.core.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@EqualsAndHashCode(exclude = {"lingos", "password"})
public class User {

    @Id
    private Long id;
    @OneToMany(mappedBy = "author")
    private List<Lingo> lingos;
    private String userName;

    private String email;
    private String password;
    private String token;
    private String refreshToken;

}
