package raf.sk.gym.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestUserServiceApplication {

	/**
	 * Instantiates and configures a MariaDB container.
	 *
	 * @return The instantiated MariaDBContainer object.
	 */
	@Bean
	@ServiceConnection
    PostgreSQLContainer<?> mariaDbContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
	}

	/**
	 * The entry point for the application.
	 *
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.from(UserServiceApplication::main).with(TestUserServiceApplication.class).run(args);
	}

}