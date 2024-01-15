package raf.sk.gym.notificationservice.consumers.objects;

import raf.sk.gym.notificationservice.consumers.objects.models.ReceiverDTO;
import raf.sk.gym.notificationservice.consumers.objects.models.TrainingDTO;

import java.util.List;

public class TrainingCancelledDTO {
    private List<ReceiverDTO> receivers;
    private TrainingDTO training;


    public List<ReceiverDTO> getReceivers() {
        return receivers;
    }

    public TrainingDTO getTraining() {
        return training;
    }
}
