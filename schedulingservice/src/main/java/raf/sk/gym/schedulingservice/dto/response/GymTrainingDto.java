package raf.sk.gym.schedulingservice.dto.response;

import raf.sk.gym.schedulingservice.model.GymTraining;

import java.math.BigDecimal;

public record GymTrainingDto(String name, BigDecimal price, String trainingType) {
    public static GymTrainingDto fromGymTraining(GymTraining gymTrainings) {
        return new GymTrainingDto(gymTrainings.getTraining()
                .getName(), gymTrainings.getPrice(), gymTrainings.getTraining()
                .getCategory());
    }
}
