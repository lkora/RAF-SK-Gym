package raf.sk.gym.userservice.dto.request;

import java.time.LocalDate;

public record EditProfile(String username, String password, String email, LocalDate dateOfBirth, String firstName,
                          String lastName) {}