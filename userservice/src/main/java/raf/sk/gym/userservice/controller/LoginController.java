package raf.sk.gym.userservice.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.sk.gym.userservice.dto.request.JwtRequest;
import raf.sk.gym.userservice.dto.request.LoginRequest;
import raf.sk.gym.userservice.dto.response.JwtResponse;
import raf.sk.gym.userservice.dto.response.JwtRoleResponse;
import raf.sk.gym.userservice.security.JwtTokenProvider;
import raf.sk.gym.userservice.service.UserService;

@Slf4j
@RestController
@RequestMapping("/auth/")
public class LoginController {


    private final AuthenticationManager authenticationManager;


    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
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
    public ResponseEntity<JwtRoleResponse> jwtValidationCheck(@RequestBody JwtRequest request) {
        log.info("Go {}",request);
        String jwt = request.jwt();
        if (jwt != null && jwt.startsWith("Bearer ")) {
            String token = jwt.substring(7)
                    .trim();
            try {
                jwtTokenProvider.validateToken(token);
                var authorities = jwtTokenProvider.getAuthentication(token)
                        .getAuthorities();
                String role = ((SimpleGrantedAuthority) authorities.toArray()[0]).getAuthority();
                return ResponseEntity.status(HttpStatus.OK)
                    .body(new JwtRoleResponse(true, role));
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new JwtRoleResponse(false, ""));
            }

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new JwtRoleResponse(false, ""));
    }
    @PostMapping("/valid/manager")
    public ResponseEntity<?> verifyManager(@RequestBody JwtRequest request) {
        String username = jwtTokenProvider.getUsernameFromToken(request.jwt());
        var userOptional = userService.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var user = userOptional.get();
        if (user.getUserType().equalsIgnoreCase("manager")) {
            var manager = userService.findManager(user.getId());
            record ManagerResponse(Boolean isManager, String gymName) {}
            return manager.map(m -> ResponseEntity.ok(new ManagerResponse(true, m.getGymName())))
                    .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}