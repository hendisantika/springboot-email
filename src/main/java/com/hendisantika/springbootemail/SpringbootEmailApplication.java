package com.hendisantika.springbootemail;

import com.hendisantika.springbootemail.config.BrevoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BrevoConfig.class)
public class SpringbootEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEmailApplication.class, args);
    }

}
