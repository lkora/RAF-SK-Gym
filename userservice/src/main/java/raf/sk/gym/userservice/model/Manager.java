package raf.sk.gym.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "manager")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Manager {

    @Id
    @Column(name = "manager_id")
    private Long managerId;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "manager_id")
    private User manager;

    @Size(max = 255)
    @Column(name = "gym_name")
    private String gymName;

    @Column(name = "hire_date")
    private LocalDate hireDate;
}