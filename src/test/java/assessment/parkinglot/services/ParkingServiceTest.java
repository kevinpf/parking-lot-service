package assessment.parkinglot.services;

import assessment.parkinglot.enums.VehicleType;
import assessment.parkinglot.models.ParkingSpace;
import assessment.parkinglot.models.ParkingTicket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ParkingServiceTest {

    @Autowired
    private ParkingService parkingService;

    @Test
    public void parkBike() {
        VehicleType type = VehicleType.BIKE;
        ParkingTicket request = new ParkingTicket();
        request.setType(type);
        request = parkingService.park(request);
        validateRequestTicket(request, type);
    }

    @Test
    public void parkCar() {
        VehicleType type = VehicleType.CAR;
        ParkingTicket request = new ParkingTicket();
        request.setType(type);
        request = parkingService.park(request);
        validateRequestTicket(request, type);
    }

    @Test
    public void parkVan() {
        VehicleType type = VehicleType.VAN;
        ParkingTicket request = new ParkingTicket();
        request.setType(type);
        request = parkingService.park(request);
        validateRequestTicket(request, type);
    }

    private void validateRequestTicket(ParkingTicket request, VehicleType type) {
        assertThat(request.getId()).isNotNull();
        assertThat(request.getType()).isEqualTo(type);
        assertThat(request.getRequestDate()).isNotNull();
        assertThat(request.getSpaces()).isNotNull();
        assertThat(request.getSpaces().size()).isEqualTo(type.getSpacesRequired());
        request.getSpaces().forEach(space -> assertThat(space.getType()).isIn(type.getSpaceTypes()));
    }

    @Test
    public void parkTooManyBikes() {
        for (int i = 0; i < 5; i++) {
            ParkingTicket request = new ParkingTicket();
            request.setType(VehicleType.BIKE);
            parkingService.park(request);
        }
        ParkingTicket request = new ParkingTicket();
        request.setType(VehicleType.BIKE);
        assertThatThrownBy(() -> parkingService.park(request)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void parkWithoutType() {
        ParkingTicket request = new ParkingTicket();
        assertThatThrownBy(() -> parkingService.park(request)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void leave() {
        ParkingTicket request = new ParkingTicket();
        request.setType(VehicleType.BIKE);
        request = parkingService.park(request);
        ParkingTicket left = parkingService.leave(request);
        assertThat(left.getId()).isEqualTo(request.getId());
        assertThat(left.getType()).isEqualTo(request.getType());
        assertThat(left.getLeftDate()).isNotNull();
        assertThat(left.getLeftDate()).isBefore(new Date());
    }

    @Test
    public void getSpaces() {
        List<ParkingSpace> spaces = parkingService.getAvailableSpaces(null);
        assertThat(spaces).isNotNull();
        assertThat(spaces.size()).isEqualTo(25);
    }

    @Test
    public void getSpacesForType() {
        List<ParkingSpace> spaces = parkingService.getAvailableSpaces(VehicleType.BIKE.name());
        assertThat(spaces).isNotNull();
        assertThat(spaces.size()).isEqualTo(5);
    }
}
