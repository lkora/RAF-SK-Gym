package raf.sk.gym.userservice.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
