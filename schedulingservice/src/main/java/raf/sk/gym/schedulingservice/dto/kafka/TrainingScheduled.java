package raf.sk.gym.schedulingservice.dto.kafka;

public record TrainingScheduled(UserDto receiver, TrainingDto training) {}
