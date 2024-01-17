package raf.sk.gym.notificationservice.consumers.objects;

import raf.sk.gym.notificationservice.consumers.objects.models.ReceiverDto;
import raf.sk.gym.notificationservice.consumers.objects.models.TrainingDto;

public record TrainingReminderDto(TrainingDto training, ReceiverDto receiver) {}
