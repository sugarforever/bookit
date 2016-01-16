package com.wiysoft;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Created by weiliyang on 7/24/15.
 */
@SpringBootApplication
@EnableConfigurationProperties({BookitConfiguration.class})
public class BookitApplication extends SpringBootServletInitializer {

    private final static Logger logger = LoggerFactory.getLogger(BookitApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BookitApplication.class, args);
        logger.info("Bookit application started.");
    }
}
