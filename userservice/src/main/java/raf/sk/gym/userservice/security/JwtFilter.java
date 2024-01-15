package raf.sk.gym.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import raf.sk.gym.userservice.dto.response.GeneralResponse;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {


    private final JwtTokenProvider tokenProvider;

    public JwtFilter(JwtTokenProvider tokenProvider) {this.tokenProvider = tokenProvider;}

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwt(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        } catch (ExpiredJwtException ex) {
            String json = new ObjectMapper().writeValueAsString(new GeneralResponse("Authentication expired, please " +
                    "log in again."));
            log.info("Authentication tried with expired token. Exception message: {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter()
                    .write(json);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            return token.trim();
        }
        return null;
    }
}
