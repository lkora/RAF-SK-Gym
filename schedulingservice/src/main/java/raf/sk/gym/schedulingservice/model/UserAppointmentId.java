package raf.sk.gym.schedulingservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UserAppointmentId implements Serializable {
    @Serial
    private static final long serialVersionUID = 3341853760762571850L;
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull
    @Column(name = "appointment_id", nullable = false)
    private Integer appointmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserAppointmentId entity = (UserAppointmentId) o;
        return Objects.equals(this.appointmentId, entity.appointmentId) && Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentId, userId);
    }

}