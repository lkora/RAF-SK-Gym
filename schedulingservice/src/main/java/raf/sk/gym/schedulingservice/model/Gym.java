package raf.sk.gym.schedulingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "gym")
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 255)
    @Column(name = "name")
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "number_of_trainers")
    private Integer numberOfTrainers;

    @OneToMany(mappedBy = "gym")
    private Set<Appointment> appointments = new LinkedHashSet<>();

    @OneToOne(mappedBy = "gym")
    private LoyalCustomerOffer loyalCustomerOffers;

    @OneToMany(mappedBy = "gym")
    private Set<TrainingType> trainingTypes = new LinkedHashSet<>();

}