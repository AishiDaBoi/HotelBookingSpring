package htl.steyr.springdesktop.repository;

import htl.steyr.springdesktop.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
