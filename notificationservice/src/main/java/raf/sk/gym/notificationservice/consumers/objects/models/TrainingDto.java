package raf.sk.gym.notificationservice.consumers.objects.models;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public record TrainingDto(String trainingName, String trainingType, LocalDateTime startTime) {}
