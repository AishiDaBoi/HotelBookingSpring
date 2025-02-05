package htl.steyr.springdesktop.repository;

import htl.steyr.springdesktop.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.id NOT IN " +
            "(SELECT rb.room.id FROM RoomBooking rb WHERE " +
            "(rb.booking.dateOfArrival < :endDate AND rb.booking.dateOfDeparture > :startDate))")
    List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate);
}
