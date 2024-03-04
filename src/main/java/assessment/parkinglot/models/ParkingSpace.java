package assessment.parkinglot.models;

import assessment.parkinglot.enums.SpaceType;
import assessment.parkinglot.enums.VehicleType;

public class ParkingSpace {

    private Long id;
    private boolean available;
    private Long ticket;
    private SpaceType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getTicket() {
        return ticket;
    }

    public void setTicket(Long ticket) {
        this.ticket = ticket;
    }

    public SpaceType getType() {
        return type;
    }

    public void setType(SpaceType type) {
        this.type = type;
    }
}
