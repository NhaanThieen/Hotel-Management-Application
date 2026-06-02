package com.app.utils;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

// Đánh dấu đây là 1 Bean (Singleton) do Spring quản lý
@Component
@PropertySource("classpath:config.properties")
public class JwtUtils {
    
    // Đọc trực tiếp key từ file properties thành String
    @Value("${token.secret.key}")
    private String secretKey; 

    // Đọc thời gian sống của token từ file properties
    @Value("${token.expire.time}")
    private long expirationMs; 

    // Dùng để tạo token
    public String generateToken(String username, String roles) throws Exception {
        JWSSigner signer = new MACSigner(secretKey);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .claim("roles", roles) 
                .expirationTime(new Date(System.currentTimeMillis() + expirationMs))
                .issueTime(new Date())
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claimsSet
        );

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    // Phân giải token thành thông tin để filter tạo đối tượng authentication
    public JWTClaimsSet validateTokenAndGetClaims(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(secretKey);

        if (signedJWT.verify(verifier)) {
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expiration.after(new Date())) {
                return signedJWT.getJWTClaimsSet();
            }
        }
        return null;
    }
}