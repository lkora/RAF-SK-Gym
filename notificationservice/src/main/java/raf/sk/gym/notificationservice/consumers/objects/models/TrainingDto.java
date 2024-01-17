package raf.sk.gym.notificationservice.consumers.objects.models;

import java.time.LocalDateTime;

public record TrainingDto(String trainingName, String trainingType, LocalDateTime startTime) {}
