package assessment.parkinglot.converters;

import assessment.parkinglot.entities.ParkingTicketEntity;
import assessment.parkinglot.models.ParkingTicket;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ParkingTicketEntityToModel implements Converter<ParkingTicketEntity, ParkingTicket> {

    @Override
    public ParkingTicket convert(ParkingTicketEntity source) {
        ParkingTicket model = new ParkingTicket();
        model.setId(source.getId());
        model.setType(source.getType());
        model.setRequestDate(source.getRequestDate());
        model.setLeftDate(source.getLeftDate());
        return model;
    }
}
