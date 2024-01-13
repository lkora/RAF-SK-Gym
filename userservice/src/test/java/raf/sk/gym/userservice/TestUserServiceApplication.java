package raf.sk.gym.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.from(UserServiceApplication::main)
                .with(TestUserServiceApplication.class)
                .run(args);
    }

    /**
     * Instantiates and configures a PostgreSQL container.
     */
    @Bean
    @ServiceConnection
    @RestartScope
    PostgreSQLContainer<?> postgresSContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
    }

}