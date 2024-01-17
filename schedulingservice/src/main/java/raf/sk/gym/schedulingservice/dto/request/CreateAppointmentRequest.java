package raf.sk.gym.schedulingservice.dto.request;

import java.math.BigDecimal;

public record CreateAppointmentRequest(String gymName, String trainingName, BigDecimal price, Integer participants,
                                       String dayOfWeek) {}
