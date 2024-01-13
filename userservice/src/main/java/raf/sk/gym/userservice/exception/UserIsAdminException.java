package raf.sk.gym.userservice.exception;

public class UserIsAdminException extends RuntimeException {

    public UserIsAdminException(String message) {
        super(message);
    }
}
