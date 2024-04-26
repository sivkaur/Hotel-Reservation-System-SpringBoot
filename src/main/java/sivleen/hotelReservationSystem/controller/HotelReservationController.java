package sivleen.hotelReservationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sivleen.hotelReservationSystem.entity.Hotel;
import sivleen.hotelReservationSystem.entity.Reservation;
import sivleen.hotelReservationSystem.service.HotelReservationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelReservationController {

    @Autowired
    private HotelReservationService hotelReservationService;

    // GET endpoint to retrieve all hotels
    @GetMapping("/hotels")
    public ResponseEntity<List<Hotel>> getAllHotels(
            @RequestParam(required = false) LocalDate checkin,
            @RequestParam(required = false) LocalDate checkout,
            @RequestParam(required = false) Integer numberOfGuests) {
        try {
            List<Hotel> availableHotels = hotelReservationService.findAvailableHotels(checkin, checkout, numberOfGuests);
            if (availableHotels.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(availableHotels, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve data", ex);
        }
    }

    @PostMapping("/hotels")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel newHotel) {
        try {
            Hotel savedHotel = hotelReservationService.saveHotel(newHotel);
            return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create hotel", ex);
        }
    }

    // POST endpoint to create a new reservation
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation newReservation) {
        try {
            Reservation savedReservation = hotelReservationService.createReservation(newReservation);
            return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            // Rethrow the exception to preserve specific error responses
            throw ex;
        } catch (Exception ex) {
            // Catch-all for other exceptions which might not have been anticipated
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create reservation", ex);
        }
    }
}