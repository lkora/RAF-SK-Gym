package raf.sk.gym.schedulingservice.dto.response;

import raf.sk.gym.schedulingservice.model.Appointment;

import java.time.LocalTime;

public record AppointmentResponse(Integer id, String trainingType, String gym, String dayOfWeek, LocalTime startTime) {

    public static AppointmentResponse fromAppointment(Appointment appointment) {
        return new AppointmentResponse(appointment.getId(), appointment.getGymTraining()
                .getTraining()
                .getName(), appointment.getGymTraining()
                .getGym()
                .getName(), appointment.getDayOfWeek(), appointment.getStartTime());
    }
}
