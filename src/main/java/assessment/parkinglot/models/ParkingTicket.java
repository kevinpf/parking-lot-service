package assessment.parkinglot.models;

import assessment.parkinglot.enums.VehicleType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class ParkingTicket {

    private Long id;
    private VehicleType type;
    @JsonProperty("request_date")
    private Date requestDate;
    @JsonProperty("left_date")
    private Date leftDate;
    private List<ParkingSpace> spaces;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getLeftDate() {
        return leftDate;
    }

    public void setLeftDate(Date leftDate) {
        this.leftDate = leftDate;
    }

    public List<ParkingSpace> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<ParkingSpace> spaces) {
        this.spaces = spaces;
    }
}
