package htl.steyr.springdesktop.repository;

import htl.steyr.springdesktop.model.Booking;
import htl.steyr.springdesktop.model.Room;
import htl.steyr.springdesktop.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {

    List<RoomBooking> findByRoom(Room room);
}
