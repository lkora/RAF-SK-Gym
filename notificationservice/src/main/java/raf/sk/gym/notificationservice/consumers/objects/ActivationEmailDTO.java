package raf.sk.gym.notificationservice.consumers.objects;

import raf.sk.gym.notificationservice.consumers.objects.models.ReceiverDTO;

public class ActivationEmailDTO {

    private ReceiverDTO receiver;
    private String link;

    public ReceiverDTO getReceiver() {
        return receiver;
    }

    public String getLink() {
        return link;
    }
}
