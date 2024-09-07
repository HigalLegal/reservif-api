package com.reservif.configs.jwt.impl;


import com.reservif.configs.jwt.TokenGenerator;
import com.reservif.entities.User;
import com.reservif.entities.enuns.TypeUser;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class TokenGeneratorImpl implements TokenGenerator {

    private long EXPIRATION_TIME_IN_SECONDS = 60 * 60 * 3;

    @Override
    public String createToken(User user) {
        Instant now = Instant.now();

        return Jwt
                .upn(user.getEmail())
                .groups(generateRoles(user.getTypeUser()))
                .claim("iat", now.getEpochSecond())
                .claim("exp", generateExpiration(EXPIRATION_TIME_IN_SECONDS, now))
                .claim("userId", user.getId())
                .claim("userFullName", user.getFirstName().concat(" ").concat(user.getLastName()))
                .claim("email", user.getEmail())
                .sign();
    }

    private Set<String> generateRoles(TypeUser typeUser) {
        String[] rolesArray = typeUser == TypeUser.TEACHER ?
                new String[]{typeUser.getTypeUser()}
                :
                new String[]{"Professor", "Administrador"};

        return new HashSet<>(Arrays.asList(rolesArray));
    }

    private long generateExpiration(long seconds, Instant begin) {
        return begin.plus(Duration.ofSeconds(seconds)).getEpochSecond();
    }
}