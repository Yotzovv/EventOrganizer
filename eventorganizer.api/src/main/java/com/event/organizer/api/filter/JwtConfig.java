package com.event.organizer.api.filter;

import com.auth0.jwt.algorithms.Algorithm;

public class JwtConfig {

    private static final String SECRET = "secret";

    public static Algorithm getAlgotithm() {
        return  Algorithm.HMAC256(SECRET);

    }
}
