package assessment.parkinglot.services;

import assessment.parkinglot.entities.ParkingSpaceEntity;
import assessment.parkinglot.entities.ParkingTicketEntity;
import assessment.parkinglot.enums.SpaceType;
import assessment.parkinglot.models.ParkingSpace;
import assessment.parkinglot.models.ParkingTicket;
import assessment.parkinglot.repositories.ParkingSpaceRepository;
import assessment.parkinglot.repositories.ParkingTicketRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ParkingService {

    private ParkingSpaceRepository parkingSpaceRepository;
    private ParkingTicketRepository parkingTicketRepository;
    private ConversionService conversionService;

    public ParkingService(ParkingSpaceRepository parkingSpaceRepository, ParkingTicketRepository parkingTicketRepository, ConversionService conversionService) {
        this.parkingSpaceRepository = parkingSpaceRepository;
        this.parkingTicketRepository = parkingTicketRepository;
        this.conversionService = conversionService;
        initialize();
    }

    // we create available parking spaces
    private void initialize() {
        List<ParkingSpaceEntity> spots = parkingSpaceRepository.findAll();
        if (CollectionUtils.isEmpty(spots)) {
            for (int i = 0; i < 5; i++) {
                ParkingSpaceEntity entity = new ParkingSpaceEntity(SpaceType.BIKE);
                parkingSpaceRepository.save(entity);
            }
            for (int i = 0; i < 10; i++) {
                ParkingSpaceEntity entity = new ParkingSpaceEntity(SpaceType.COMPACT);
                parkingSpaceRepository.save(entity);
            }
            for (int i = 0; i < 10; i++) {
                ParkingSpaceEntity entity = new ParkingSpaceEntity(SpaceType.REGULAR);
                parkingSpaceRepository.save(entity);
            }
        }
    }

    public ParkingTicket park(ParkingTicket ticket) {
        List<ParkingSpaceEntity> spaces = getSpaces(ticket);
        ParkingTicketEntity ticketEntity = new ParkingTicketEntity(ticket.getType());
        parkingTicketRepository.save(ticketEntity);
        ticket = conversionService.convert(ticketEntity, ParkingTicket.class);
        ticket.setSpaces(spaces.stream()
                .map(space -> space.park(ticketEntity))
                .map(parkingSpaceRepository::save)
                .map(this::convert)
                .toList());
        return ticket;
    }

    public ParkingTicket leave(ParkingTicket ticket) {
        return parkingTicketRepository
                .findById(ticket.getId())
                .map(this::validateLeave)
                .map(this::doLeave)
                .map(this::convert)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public List<ParkingSpace> getAvailableSpaces(String typeStr) {
        List<ParkingSpaceEntity> entities;
        if (typeStr == null) {
            entities = parkingSpaceRepository.findByAvailable(true);
        } else {
            SpaceType type = SpaceType.valueOf(typeStr);
            entities = parkingSpaceRepository.findByTypeInAndAvailable(Arrays.asList(type), true);
        }
        if (!CollectionUtils.isEmpty(entities)) {
            return entities.stream().map(this::convert).toList();
        }
        return new ArrayList<>();
    }

    private List<ParkingSpaceEntity> getSpaces(ParkingTicket ticket) {
        if (ticket.getType() == null) {
            throw new RuntimeException("Vehicle type is required");
        }
        List<ParkingSpaceEntity> spaces = parkingSpaceRepository
                .findByTypeInAndAvailable(ticket.getType().getSpaceTypes(), true);
        if (CollectionUtils.isEmpty(spaces) || spaces.size() < ticket.getType().getSpacesRequired()) {
            throw new RuntimeException("No spaces available for required vehicle type");
        }
        return ticket.getType().getSpaces(spaces);
    }

    private ParkingTicketEntity doLeave(ParkingTicketEntity parkingTicketEntity) {
        parkingTicketEntity.leave();
        parkingSpaceRepository.findByTicket(parkingTicketEntity.getId())
                .stream()
                .map(ParkingSpaceEntity::leave)
                .map(parkingSpaceRepository::save)
                .toList();
        return parkingTicketEntity;
    }

    private ParkingTicket convert(ParkingTicketEntity entity) {
        return conversionService.convert(entity, ParkingTicket.class);
    }
    private ParkingSpace convert(ParkingSpaceEntity entity) {
        return conversionService.convert(entity, ParkingSpace.class);
    }

    private ParkingTicketEntity validateLeave(ParkingTicketEntity parkingTicketEntity) {
        if (parkingTicketEntity.getLeftDate() != null) {
            throw new RuntimeException("Ticket already left");
        }
        return parkingTicketEntity;
    }
}
