package sivleen.hotelReservationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sivleen.hotelReservationSystem.entity.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
}
