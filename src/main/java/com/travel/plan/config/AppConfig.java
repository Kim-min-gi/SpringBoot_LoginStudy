package com.travel.plan.config;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Base64;

//@Configuration
@Data
@ConfigurationProperties(prefix = "keynumber")
public class AppConfig {

    private String jwtKey;

    public SecretKey getJwtKey(){
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtKey));
    }


}
