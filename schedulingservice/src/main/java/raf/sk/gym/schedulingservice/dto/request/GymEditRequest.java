package raf.sk.gym.schedulingservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GymEditRequest(Integer id, @Size(max = 255) String name, String description,
                             @Min(0) @NotNull Integer numberOfTrainers) {}
