package raf.sk.gym.schedulingservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import raf.sk.gym.schedulingservice.dto.response.ErrorResponse;
import raf.sk.gym.schedulingservice.service.AuthService;

import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    public TokenAuthenticationFilter(AuthService authService) {this.authService = authService;}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (!authService.authenticate(token)) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(new ErrorResponse("Unauthorized"));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter()
                    .write(json);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
