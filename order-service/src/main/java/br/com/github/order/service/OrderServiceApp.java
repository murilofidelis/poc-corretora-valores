package br.com.github.order.service;

import br.com.github.order.service.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class OrderServiceApp {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(OrderServiceApp.class);
        Environment env = app.run(args).getEnvironment();
        log.info("""
                        
                        ----------------------------------------------------------
                        "Service: '{}' init! Access URLs:
                        "Internal: http://localhost:{}
                        "External: http://{}:{}
                        ----------------------------------------------------------""",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }

}
