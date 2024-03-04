package assessment.parkinglot.entities;

import assessment.parkinglot.enums.SpaceType;
import jakarta.persistence.*;

@Entity
@Table(name = "parking_space")
public class ParkingSpaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private SpaceType type;
    private boolean available;
    private Long ticket;

    public ParkingSpaceEntity() {
    }

    public ParkingSpaceEntity(SpaceType type) {
        this.type = type;
        this.available = true;
    }

    public Long getId() {
        return id;
    }

    public SpaceType getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public Long getTicket() {
        return ticket;
    }

    public ParkingSpaceEntity park(ParkingTicketEntity ticket) {
        this.ticket = ticket.getId();
        this.available = false;
        return this;
    }

    public ParkingSpaceEntity leave() {
        this.ticket = null;
        this.available = true;
        return this;
    }
}
