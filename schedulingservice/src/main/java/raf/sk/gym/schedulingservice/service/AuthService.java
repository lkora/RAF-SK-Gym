package raf.sk.gym.schedulingservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import raf.sk.gym.schedulingservice.client.JwtClient;
import raf.sk.gym.schedulingservice.dto.server.request.JwtRequest;
import raf.sk.gym.schedulingservice.dto.server.response.ManagerVerificationResponse;
import raf.sk.gym.schedulingservice.dto.server.response.TokenResponse;

import java.util.function.Function;

@Service
@Slf4j
public class AuthService {

    private final JwtClient client;

    public AuthService(JwtClient client) {this.client = client;}


    public boolean authenticate(String token) {
        return verifyToken(token, TokenResponse::isValid);
    }

    public boolean isManager(String token) {
        return client.verifyManager(new JwtRequest(token))
                .isManager();
    }
    public ManagerVerificationResponse attemptManagerVerification(String token) {
       try {
            return client.verifyManager(new JwtRequest(token));
       } catch (RestClientException ex) {
           log.info(ex.getMessage());
           return new ManagerVerificationResponse(false, "");
       }
    }

    public boolean isClient(String token) {
        return verifyToken(token, tokenResponse -> tokenResponse.role()
                .equalsIgnoreCase("client"));
    }

    private boolean verifyToken(String token, Function<TokenResponse, Boolean> tokenChecker) {
        try {
            return tokenChecker.apply(client.checkToken(new JwtRequest(token)));
        } catch (RestClientException exception) {
            log.warn("Authorization attempted with invalid or missing token: {}", token);
            log.warn(exception.getMessage());
            return false;
        }
    }
}
