package com.togo.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @Author taiyn
 * @Description TODO
 * @Date 4:23 下午 2020/2/14
 **/
public class JwtDemo {

    public static void main(String[] args) {

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String secretString = Encoders.BASE64.encode(key.getEncoded());
        String secretString = "M353jnvv6us4BkICh7MHwN5adrFPX3REX/MakebIAyk=";

        Key speKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
        System.out.println("key: " + secretString);
        String jws = Jwts.builder().setSubject("haha").signWith(speKey).compact();
        System.out.println(jws);
        String body = Jwts.parserBuilder().setSigningKey(speKey).build().parseClaimsJws(jws).toString();
        System.out.println(body);

    }
}
