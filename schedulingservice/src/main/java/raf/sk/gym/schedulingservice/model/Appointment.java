package raf.sk.gym.schedulingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "gym_training_id")
    private GymTraining gymTraining;

    @Size(max = 9)
    @Column(name = "day_of_week", length = 9)
    private String dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "participants")
    private Integer participants;

    @Column(name = "registered_users")
    private Integer registeredUsers;

    @Column(name = "is_cancelled")
    private Boolean isCancelled;

    @Column(name = "cancelled_by")
    private Integer cancelledBy;

}