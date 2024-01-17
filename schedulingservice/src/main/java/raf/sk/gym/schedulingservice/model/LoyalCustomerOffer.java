package raf.sk.gym.schedulingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "loyal_customer_offer")
public class LoyalCustomerOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Column(name = "number_of_appointments")
    private Integer numberOfAppointments;

    @Column(name = "benefit_description", length = Integer.MAX_VALUE)
    private String benefitDescription;

}