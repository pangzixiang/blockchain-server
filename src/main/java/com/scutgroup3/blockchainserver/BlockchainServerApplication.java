package com.scutgroup3.blockchainserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
public class BlockchainServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainServerApplication.class, args);
    }

}
