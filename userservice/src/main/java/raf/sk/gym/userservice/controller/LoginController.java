package raf.sk.gym.userservice.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.username(),
                    loginRequest.password());
            Authentication authenticated = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext()
                    .setAuthentication(authenticated);
            String jwt = jwtTokenProvider.generateToken(authenticated);
            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new GeneralResponse("Invalid username or password."));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new GeneralResponse("User is disabled"));
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new GeneralResponse("User account is locked"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new GeneralResponse("User account is not activated or banned"));
        }
    }
}
