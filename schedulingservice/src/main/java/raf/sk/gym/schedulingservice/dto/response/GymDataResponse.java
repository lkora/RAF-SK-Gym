package raf.sk.gym.schedulingservice.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import raf.sk.gym.schedulingservice.model.Gym;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link raf.sk.gym.schedulingservice.model.Gym}
 */
public record GymDataResponse(@Size(max = 255) String name, String description,
                              @Min(0) @NotNull Integer numberOfTrainers, Set<GymTrainingDto> trainingTypes) {
    public static GymDataResponse fromGym(Gym gym) {
        var set = gym.getGymTrainings()
                .stream()
                .map(GymTrainingDto::fromGymTraining)
                .collect(Collectors.toUnmodifiableSet());
        return new GymDataResponse(gym.getName(), gym.getDescription(), gym.getNumberOfTrainers(), set);
    }
}