package assessment.parkinglot.repositories;

import assessment.parkinglot.entities.ParkingTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicketEntity, Long> {
}
