package com.libwy.lingo.api.rest;


import com.libwy.lingo.api.UserService;
import com.libwy.lingo.api.dto.LoginDto;
import com.libwy.lingo.api.dto.UserCreateDTO;
import com.libwy.lingo.api.security.JWTToken;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import static com.libwy.lingo.api.config.SecurityConfiguration.AUTHORITIES_KEY;
import static com.libwy.lingo.api.config.SecurityConfiguration.JWT_ALGORITHM;

@RestController
public class AuthenticateController {

    @Value("${my.security.authentication.jwt.token-validity-in-seconds:0}")
    private long tokenValidityInSeconds;

    @Value("${my.security.authentication.jwt.token-validity-in-seconds-for-remember-me:0}")
    private long tokenValidityInSecondsForRememberMe;
    private final JwtEncoder jwtEncoder;
    private final UserService userService;

    public AuthenticateController(final AuthenticationManagerBuilder authenticationManagerBuilder,
                                  final JwtEncoder jwtEncoder,
                                  final UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtEncoder = jwtEncoder;
        this.userService = userService;
    }

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/api/authenticate")
    public ResponseEntity<JWTToken> authenticate(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(
                usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.createToken(authentication, loginDto.getRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);

    }
    @PostMapping("/api/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody UserCreateDTO dto) {

        this.userService.registerUser(dto);

    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity;
        if (rememberMe) {
            validity = now.plus(this.tokenValidityInSecondsForRememberMe, ChronoUnit.SECONDS);
        } else {
            validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);
        }

        // @formatter:off
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuedAt(now)
                    .expiresAt(validity)
                    .subject(authentication.getName())
                    .claim(AUTHORITIES_KEY, authorities)
                    .build();

            JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
            return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }


}
