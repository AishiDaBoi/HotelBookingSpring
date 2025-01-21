package htl.steyr.springdesktop.repository;

import htl.steyr.springdesktop.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
//    @Query(nativeQuery = true, value = "SELECT * FROM room WHERE customer_id = ?")
//    Room getRoomsByCustomer(long customerId);
}
