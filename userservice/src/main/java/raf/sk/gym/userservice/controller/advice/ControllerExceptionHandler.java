package raf.sk.gym.userservice.controller.advice;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import raf.sk.gym.userservice.dto.response.GeneralResponse;
import raf.sk.gym.userservice.exception.UserIsAdminException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserIsAdminException.class)
    public ResponseEntity<GeneralResponse> handleUserIsAdminException(UserIsAdminException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GeneralResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponse(e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GeneralResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GeneralResponse(e.getMessage()));
    }
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<GeneralResponse> handleDisabledException(DisabledException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new GeneralResponse(e.getMessage()));
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<GeneralResponse> handleLockedException(LockedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new GeneralResponse(e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GeneralResponse> handleAccessDeniedException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new GeneralResponse(e.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<GeneralResponse> handleExpiredJwt(ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GeneralResponse(e.getMessage()));
    }
}
