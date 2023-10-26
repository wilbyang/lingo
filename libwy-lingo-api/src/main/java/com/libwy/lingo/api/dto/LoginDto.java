package com.libwy.lingo.api.dto;


import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
    private Boolean rememberMe;
}
