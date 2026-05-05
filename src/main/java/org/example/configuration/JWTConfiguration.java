package org.example.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JWTConfiguration {

    @Bean
    SecretKey secretKey(){
        MacAlgorithm macAlgorithm = Jwts.SIG.HS256;
        return macAlgorithm.key().build();
    }

}
