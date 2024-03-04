package assessment.parkinglot.controllers;

import assessment.parkinglot.models.ParkingSpace;
import assessment.parkinglot.models.ParkingTicket;
import assessment.parkinglot.services.ParkingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingController {

    private ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping
    public ParkingTicket park(@RequestBody ParkingTicket parkingTicket) {
        return parkingService.park(parkingTicket);
    }

    @PostMapping("leave")
    public ParkingTicket leave(@RequestBody ParkingTicket parkingTicket) {
        return parkingService.leave(parkingTicket);
    }

    @GetMapping
    public List<ParkingSpace> getAvailable(@RequestParam(value = "type", required = false) String type) {
        return parkingService.getAvailableSpaces(type);
    }

}
