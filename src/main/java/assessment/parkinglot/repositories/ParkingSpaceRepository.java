package assessment.parkinglot.repositories;

import assessment.parkinglot.entities.ParkingSpaceEntity;
import assessment.parkinglot.enums.SpaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpaceEntity, Long> {

    List<ParkingSpaceEntity> findByAvailable(boolean available);
    List<ParkingSpaceEntity> findByTypeInAndAvailable(List<SpaceType> type, boolean available);

    List<ParkingSpaceEntity> findByTicket(Long ticket);
}
