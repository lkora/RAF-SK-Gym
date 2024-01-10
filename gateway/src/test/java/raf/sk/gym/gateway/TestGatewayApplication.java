package raf.sk.gym.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
/**
 * Provides live reload capabilities
 * */
@TestConfiguration(proxyBeanMethods = false)
public class TestGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.from(GatewayApplication::main).with(TestGatewayApplication.class).run(args);
    }
}
