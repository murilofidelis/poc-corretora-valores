package br.com.github.homebroker.api;

import br.com.github.homebroker.api.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class HomeBrokerApi {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(HomeBrokerApi.class);
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
