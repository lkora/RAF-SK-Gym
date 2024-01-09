package raf.sk.gym.userservice.model;


import org.springframework.data.annotation.Id;

import java.time.LocalDate;

/**
 * The Manager class represents a manager in a gym. It contains information such as the manager's ID,
 * user information, gym name, and employment date.
 *
 * <p>
 * It is implemented as a record class, which automatically generates methods such as getters, equals(),
 * and hashCode() based on the provided fields.
 * </p>
 */
public record Manager(@Id Long id, UserInfo userInfo, String gymName, LocalDate employmentDate) {
}
