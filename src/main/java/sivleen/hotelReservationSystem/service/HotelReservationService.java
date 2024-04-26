package sivleen.hotelReservationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sivleen.hotelReservationSystem.entity.Hotel;
import sivleen.hotelReservationSystem.entity.Reservation;
import sivleen.hotelReservationSystem.repository.HotelRepository;
import sivleen.hotelReservationSystem.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelReservationService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    // Fetch all hotels from the database
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    // Fetch all hotels with available rooms
    public List<Hotel> findAvailableHotels(LocalDate checkIn, LocalDate checkOut, Integer guests) {
        if (checkIn == null || checkOut == null || guests == null) {
            // If no parameters are specified, return all hotels
            return hotelRepository.findAll();
        }

        // Fetch all hotels and filter based on availability
        return hotelRepository.findAll().stream()
                .filter(hotel -> isAvailable(hotel, checkIn, checkOut, guests))
                .collect(Collectors.toList());
    }

    public Hotel saveHotel(Hotel hotel) {
        // Logic to save the hotel to the database
        return hotelRepository.save(hotel);
    }

    // Create a new reservation with availability check
    @Transactional
    public Reservation createReservation(Reservation newReservation) {
        // Check if the hotel exists
        Hotel hotel = hotelRepository.findById(newReservation.getHotel().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel ID"));

        // Check availability for the requested dates
        if (!isAvailable(hotel, newReservation.getCheckinDate(), newReservation.getCheckoutDate(), newReservation.getNumberOfRoomsBooked())) {
            throw new IllegalStateException("Requested number of rooms not available for the given date range.");
        }

        // Save the new reservation if rooms are available
        return reservationRepository.save(newReservation);
    }

    // Check availability of rooms for a given date range
    private boolean isAvailable(Hotel hotel, LocalDate checkIn, LocalDate checkOut, int roomsRequested) {
        List<Reservation> overlappingReservations = reservationRepository.findReservationsByDateRangeAndHotel(hotel.getId(), checkIn, checkOut);
        int totalRoomsBooked = overlappingReservations.stream()
                .collect(Collectors.summingInt(Reservation::getNumberOfRoomsBooked));

        return (hotel.getTotalRooms() - totalRoomsBooked) >= roomsRequested;
    }
}

