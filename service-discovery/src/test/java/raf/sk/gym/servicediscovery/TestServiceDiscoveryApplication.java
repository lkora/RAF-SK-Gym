package raf.sk.gym.servicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestServiceDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.from(ServiceDiscoveryApplication::main)
                .with(TestServiceDiscoveryApplication.class)
                .run(args);
    }
}
