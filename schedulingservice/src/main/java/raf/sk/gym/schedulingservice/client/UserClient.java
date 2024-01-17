package raf.sk.gym.schedulingservice.client;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestClientException;
import org.springframework.web.service.annotation.GetExchange;
import raf.sk.gym.schedulingservice.dto.kafka.UserDto;
public interface UserClient {
    @GetExchange("http://localhost:8082/api/profile/simple")
    UserDto findUser(@RequestHeader("Authorization") String token) throws RestClientException;
}
