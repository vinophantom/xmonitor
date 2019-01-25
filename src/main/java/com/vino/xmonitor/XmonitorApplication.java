package com.vino.xmonitor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.boot.SpringApplication.*;

/**
 * @author phantom
 */
@SpringBootApplication
@EnableScheduling
public class XmonitorApplication {

    public static void main(String[] args) {
        run(XmonitorApplication.class, args);
//        SpringApplication app = new SpringApplication(XmonitorApplication.class);
////        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);
    }

}

