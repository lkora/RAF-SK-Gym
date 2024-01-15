package raf.sk.gym.notificationservice.consumers.objects;

import raf.sk.gym.notificationservice.consumers.objects.models.ReceiverDTO;
import raf.sk.gym.notificationservice.consumers.objects.models.TrainingDTO;

public class TrainingReminderDTO {

    private TrainingDTO training;
    private ReceiverDTO receiver;

    public ReceiverDTO getReceiver() {
        return receiver;
    }

    public TrainingDTO getTraining() {
        return training;
    }
}
