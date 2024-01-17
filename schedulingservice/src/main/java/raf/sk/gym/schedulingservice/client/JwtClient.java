package raf.sk.gym.schedulingservice.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.service.annotation.PostExchange;
import raf.sk.gym.schedulingservice.dto.server.request.JwtRequest;
import raf.sk.gym.schedulingservice.dto.server.response.TokenResponse;

public interface JwtClient {

    @PostExchange("http://localhost:8082/api/auth/role")
    TokenResponse checkToken(@RequestBody JwtRequest token) throws RestClientException;
}
