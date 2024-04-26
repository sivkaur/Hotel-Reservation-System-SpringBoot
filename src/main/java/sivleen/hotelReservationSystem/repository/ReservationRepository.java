package sivleen.hotelReservationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sivleen.hotelReservationSystem.entity.Reservation;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.hotel.id = :hotelId AND " +
            "(r.checkinDate <= :checkout AND r.checkoutDate >= :checkin)")
    List<Reservation> findReservationsByDateRangeAndHotel(
            @Param("hotelId") Long hotelId,
            @Param("checkin") LocalDate checkin,
            @Param("checkout") LocalDate checkout);
}