package com.travel.plan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//@Configuration
@Data
@ConfigurationProperties(prefix = "keynumber")
public class AppConfig {

    public String local;
    
}
