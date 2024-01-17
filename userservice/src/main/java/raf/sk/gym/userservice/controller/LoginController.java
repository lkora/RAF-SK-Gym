package raf.sk.gym.userservice.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.sk.gym.userservice.dto.request.JwtRequest;
import raf.sk.gym.userservice.dto.request.LoginRequest;
import raf.sk.gym.userservice.dto.response.GeneralResponse;
import raf.sk.gym.userservice.dto.response.JwtResponse;
import raf.sk.gym.userservice.security.JwtTokenProvider;

@RestController
@RequestMapping("/auth/")
public class LoginController {


    private final AuthenticationManager authenticationManager;


    private final JwtTokenProvider jwtTokenProvider;


    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.username(),
                loginRequest.password());
        Authentication authenticated = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext()
                .setAuthentication(authenticated);
        String jwt = jwtTokenProvider.generateToken(authenticated);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/valid")
    public ResponseEntity<GeneralResponse> jwtValidationCheck(@RequestBody JwtRequest jwt) {
        try {
            jwtTokenProvider.validateToken(jwt.jwt());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse("Jwt isn't valid!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponse("Jwt is valid!"));
    }
}
