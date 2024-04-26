package sivleen.hotelReservationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sivleen.hotelReservationSystem.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}