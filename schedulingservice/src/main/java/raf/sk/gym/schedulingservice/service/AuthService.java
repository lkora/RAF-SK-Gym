package raf.sk.gym.schedulingservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import raf.sk.gym.schedulingservice.client.JwtClient;
import raf.sk.gym.schedulingservice.dto.server.request.JwtRequest;
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
        return verifyToken(token, tokenResponse -> tokenResponse.role()
                .equalsIgnoreCase("manager"));
    }

    private boolean verifyToken(String token, Function<TokenResponse, Boolean> tokenChecker) {
        try {
            return tokenChecker.apply(client.checkToken(new JwtRequest(token)));
        } catch (RestClientException exception) {
            log.warn("Authorization attempted with invalid or missing token");
            return false;
        }
    }
}
