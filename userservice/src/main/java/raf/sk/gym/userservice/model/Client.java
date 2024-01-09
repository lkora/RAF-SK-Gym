package raf.sk.gym.userservice.model;

import jakarta.persistence.Id;

/**
 * The Client class represents a client entity in the system.
 *
 * It contains information such as the client's unique identifier, user information,
 * card number, and the number of scheduled trainings.
 */
public record Client(@Id Long id, UserInfo userInfo, String cardNumber, Integer numOfScheduledTrainings) {
}
