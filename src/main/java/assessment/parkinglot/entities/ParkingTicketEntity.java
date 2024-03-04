package assessment.parkinglot.entities;

import assessment.parkinglot.enums.VehicleType;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "parking_ticket")
public class ParkingTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    @Column(name = "request_date")
    private Date requestDate;
    @Column(name = "left_date")
    private Date leftDate;

    public ParkingTicketEntity() {
    }

    public ParkingTicketEntity(VehicleType type) {
        this.requestDate = new Date();
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Date getLeftDate() {
        return leftDate;
    }

    public void leave() {
        this.leftDate = new Date();
    }
}
