package raf.sk.gym.schedulingservice.dto.kafka;

import java.time.LocalTime;

public record TrainingDto(String trainingName, String trainingType, LocalTime startTime) {}
