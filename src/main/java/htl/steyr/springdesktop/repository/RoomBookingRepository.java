package htl.steyr.springdesktop.repository;

import htl.steyr.springdesktop.model.Booking;
import htl.steyr.springdesktop.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {

}
