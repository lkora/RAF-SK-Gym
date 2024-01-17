package raf.sk.gym.notificationservice.consumers.objects.models;

import java.time.LocalTime;

public record TrainingDto(String trainingName, String trainingType, LocalTime startTime) {}
