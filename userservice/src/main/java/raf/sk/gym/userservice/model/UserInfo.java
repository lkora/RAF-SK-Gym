package raf.sk.gym.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * The UserInfo class represents user information.
 *
 * <p>
 * It is a record class that includes the following information:
 *   - user ID
 *   - username
 *   - password
 *   - email
 *   - date of birth
 *   - first name
 *   - last name
 *   - type of user
 * </p>
 *
 * <p>
 * It is used as a member variable in various other classes to store user information.
 * </p>
 *
 * <p>
 * Note: This class does not provide any additional methods beyond the automatically generated ones
 * from the record class.
 * </p>
 */
public record UserInfo(
        @Id Long id,
        String username,
        String password,
        String email,
        @Column(name = "dob") LocalDate dateOfBirth,
        String firstName,
        String lastName,
        String userType) {
}
