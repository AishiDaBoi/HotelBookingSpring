package htl.steyr.springdesktop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "room_booking")
public class RoomBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public RoomBooking() {}

    // Getter und Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
}
