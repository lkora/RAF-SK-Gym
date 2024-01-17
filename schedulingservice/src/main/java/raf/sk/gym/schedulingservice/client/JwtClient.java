package raf.sk.gym.schedulingservice.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.service.annotation.PostExchange;
import raf.sk.gym.schedulingservice.dto.server.request.JwtRequest;
import raf.sk.gym.schedulingservice.dto.server.response.ManagerVerificationResponse;
import raf.sk.gym.schedulingservice.dto.server.response.TokenResponse;
public interface JwtClient {
// RETRY CRASHES SPRING?!??!?!?!?!?!?!
//    @Retryable(backoff = @Backoff(delay = 100L, multiplier = 3.0), maxAttempts = 5)
    @PostExchange("http://localhost:8082/api/auth/valid")
    TokenResponse checkToken(@RequestBody JwtRequest token) throws RestClientException;
//    @Retryable(backoff = @Backoff(delay = 2000L, multiplier = 3.0), maxAttempts = 5)
    @PostExchange("http://localhost:8082/api/auth/valid/manager")
    ManagerVerificationResponse verifyManager(@RequestBody JwtRequest token) throws RestClientException;
}
