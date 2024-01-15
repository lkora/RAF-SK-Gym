package raf.sk.gym.notificationservice.consumers.objects.models;

import java.time.LocalDateTime;

public class TrainingDTO {
    private String trainingName;
    private String trainingType;
    private LocalDateTime startTime;


    public String getTrainingType() {
        return trainingType;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
