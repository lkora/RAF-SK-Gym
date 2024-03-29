package raf.sk.gym.schedulingservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import raf.sk.gym.schedulingservice.client.JwtClient;
import raf.sk.gym.schedulingservice.client.UserClient;

@Configuration
@EnableRetry
public class RestClientConfig {

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
    @Bean
    public JwtClient jwtClient(RestClient client) {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(client))
                .build()
                .createClient(JwtClient.class);
    }

    @Bean
    public UserClient userClient(RestClient client) {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(client))
                .build()
                .createClient(UserClient.class);
    }
}
