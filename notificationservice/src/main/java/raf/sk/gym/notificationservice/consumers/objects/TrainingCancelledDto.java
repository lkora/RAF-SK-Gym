package raf.sk.gym.notificationservice.consumers.objects;

import raf.sk.gym.notificationservice.consumers.objects.models.ReceiverDto;
import raf.sk.gym.notificationservice.consumers.objects.models.TrainingDto;

import java.util.List;

public record TrainingCancelledDto(List<ReceiverDto> receivers, TrainingDto training) {}
