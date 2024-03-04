package assessment.parkinglot.converters;

import assessment.parkinglot.entities.ParkingSpaceEntity;
import assessment.parkinglot.entities.ParkingTicketEntity;
import assessment.parkinglot.models.ParkingSpace;
import assessment.parkinglot.models.ParkingTicket;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ParkingSpaceEntityToModel implements Converter<ParkingSpaceEntity, ParkingSpace> {

    @Override
    public ParkingSpace convert(ParkingSpaceEntity source) {
        ParkingSpace model = new ParkingSpace();
        model.setId(source.getId());
        model.setType(source.getType());
        model.setAvailable(source.isAvailable());
        model.setTicket(source.getTicket());
        return model;
    }
}
