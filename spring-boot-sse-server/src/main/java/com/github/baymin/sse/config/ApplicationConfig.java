package com.github.baymin.sse.config;

import com.zaxxer.hikari.HikariDataSource;
import org.cyclonedx.parsers.JsonParser;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer, InfoContributor, InitializingBean {

    private final Resource bomFile;
    private final JsonParser jsonParser = new JsonParser();
    private @Nullable List<Dependency> dependencies;

    ApplicationConfig(@Value("classpath:bom.json") Resource bomFile) {
        this.bomFile = bomFile;
    }

    @Override
    public void contribute(Info.Builder builder) {
        if (dependencies != null) {
            builder.withDetail("dependencies", dependencies);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (bomFile.exists()) {
            try (var is = bomFile.getInputStream()) {
                var bom = jsonParser.parse(is);
                this.dependencies = bom.getComponents()
                        .stream()
                        .map(Dependency::new)
                        .collect(Collectors.toList());
            }
        }
    }

    record Dependency(String groupId, String artifactId, String version) {
        Dependency(org.cyclonedx.model.Component component) {
            this(component.getGroup(), component.getName(), component.getVersion());
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .allowedOrigins(CorsConfiguration.ALL)
                .allowCredentials(true);
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.memh2")
    public DataSourceProperties memDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "memDataSource")
    public HikariDataSource memDataSource() {
        return memDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "memJdbcTemplate")
    public JdbcTemplate memJdbcTemplate() {
        return new JdbcTemplate(memDataSource());
    }

    @Bean(name = "memNamedJdbcTemplate")
    public NamedParameterJdbcTemplate memNamedJdbcTemplate() {
        return new NamedParameterJdbcTemplate(memDataSource());
    }

    /**
     * 添加h2控制台的映射地址
     */
    @Bean
    public ServletRegistrationBean<WebServlet> h2servletRegistration() {
        ServletRegistrationBean<WebServlet> registrationBean = new ServletRegistrationBean<>(new WebServlet());
        registrationBean.addUrlMappings("/h2-console/*");
        return registrationBean;
    }
}
