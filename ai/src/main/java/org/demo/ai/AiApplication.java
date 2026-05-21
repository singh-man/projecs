package org.demo.ai;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class AiApplication {

    @Value("${server.port:8080}")
    private String port;

    private String host = InetAddress.getLocalHost().getHostAddress();

    public AiApplication() throws UnknownHostException {
    }

    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println(String.format("Using Spring-Doc-OpenAPI- \n" +
                "http://%s:%s/v3/api-docs/ \n" +
                "http://%s:%s/swagger-ui/index.html \n", this.host, port, this.host, port));
    }
}
