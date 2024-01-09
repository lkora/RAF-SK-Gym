package raf.sk.gym.userservice.model;


import jakarta.persistence.Id;

/**
 * The Admin class represents an administrative user.
 * It is a record class that includes the user's ID and user information.
 */
public record Admin(@Id Long id, UserInfo userInfo) {
}
