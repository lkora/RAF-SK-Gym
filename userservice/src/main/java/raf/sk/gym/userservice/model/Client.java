package raf.sk.gym.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Client {

    @Id
    @Column(name = "client_id")
    private Long clientId;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "client_id")
    private User client;

    @Column(name = "member_card_number")
    private Long memberCardNumber;

    @Column(name = "number_of_scheduled_trainings")
    private Long numberOfScheduledTrainings;

    @Override
    public String toString() {
        return "Client{" + "clientId=" + clientId + ", client=" + client + ", memberCardNumber=" + memberCardNumber + ", numberOfScheduledTrainings=" + numberOfScheduledTrainings + '}';
    }
}