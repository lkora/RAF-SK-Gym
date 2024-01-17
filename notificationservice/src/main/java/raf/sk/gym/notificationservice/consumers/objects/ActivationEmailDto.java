package raf.sk.gym.notificationservice.consumers.objects;

import raf.sk.gym.notificationservice.consumers.objects.models.ReceiverDto;

public record ActivationEmailDto(ReceiverDto receiver, String link) {
}
