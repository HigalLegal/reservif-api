package com.reservif.configs.jwt;

import com.reservif.entities.User;

public interface TokenGenerator {

    String createToken(User user);

}
