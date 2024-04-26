package sivleen.hotelReservationSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private Integer numberOfRoomsBooked;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Guest> guests;
}
