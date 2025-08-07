package com.onnitech.pixwebhook;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
)
public class PixWebhookApplication {

    @Value("${server.port}")
    private int port;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${spring.application.version}")
    private String version;
    @Value("${spring.application.git-hash}")
    private String gitHash;

    private static final String TIMEZONE = "America/Sao_Paulo";

    @PostConstruct
    private void message() {
        log.info("Visit: http://localhost:{}{}/swagger-ui/index.html", port, contextPath);
        log.info("Version: {} - Git Hash: {}", version, gitHash);
    }

    public static void main(String[] args) {
        configureApplication(new SpringApplicationBuilder(PixWebhookApplication.class)).run(args);
    }

    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        Locale.setDefault(new Locale("pt", "BR"));
        TimeZone.setDefault(TimeZone.getTimeZone(TIMEZONE));
        log.info("[>>>TIMEZONE<<<] Spring boot application running in {}: {}", TIMEZONE, new Date());
        return builder.sources(PixWebhookApplication.class).bannerMode(Banner.Mode.OFF);
    }

}
