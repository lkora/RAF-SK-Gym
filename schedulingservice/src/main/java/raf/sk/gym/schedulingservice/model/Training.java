package raf.sk.gym.schedulingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 10)
    @Column(name = "category", length = 10)
    private String category;

    @OneToMany(mappedBy = "training")
    private Set<GymTraining> gymTrainings = new LinkedHashSet<>();

}