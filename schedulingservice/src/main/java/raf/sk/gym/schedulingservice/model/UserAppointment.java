package raf.sk.gym.schedulingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_appointments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAppointment {
    @EmbeddedId
    private UserAppointmentId id;

    @MapsId("appointmentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

}