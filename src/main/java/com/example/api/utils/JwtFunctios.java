package com.example.api.utils;

import java.util.Date;

import com.example.api.model.User;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;

public class JwtFunctios {
    public static final String SECRET ="secret";

    public String createJwtFromString(User user) {
        return Jwts.builder()
            .setId(user.getId()+"")
            .setAudience("audience")
            .setIssuer("issuer")
            .setSubject(user.getNick())
            .setPayload("payload-as-json")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, SECRET)
            .compact();
    }

    public boolean check(String jwt) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserFromJwt(String jwt) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody().getSubject();
    }

    public boolean isJwtExpired(String jwt) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody().getExpiration().before(new Date());
    }

    public boolean isJwtValid(String jwt) {
        return check(jwt) && !isJwtExpired(jwt);
    }
    /*
    Para que este código funcione adecuadamente, es necesario agregar la dependencia de JWT en el archivo pom.xml:
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.9.1</version>
    </dependency>
    */
}
