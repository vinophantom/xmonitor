package com.vino.xmonitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import static org.springframework.boot.SpringApplication.*;

/**
 * @author phantom
 */
@SpringBootApplication
@EnableScheduling
//@EnableWebSocket
public class XmonitorApplication {

    public static void main(String[] args) {
        run(XmonitorApplication.class, args);
//        SpringApplication app = new SpringApplication(XmonitorApplication.class);
////        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);
    }

}

