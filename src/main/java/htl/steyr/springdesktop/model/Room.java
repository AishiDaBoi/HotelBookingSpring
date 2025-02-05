package htl.steyr.springdesktop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "room_number", nullable = false, unique = true)
    private int roomNumber;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomBooking> roomBookings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_category_id")
    private RoomCategory roomCategory;

    public Room() {}

    // Getter und Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public RoomCategory getRoomCategory() { return roomCategory; }
    public void setRoomCategory(RoomCategory roomCategory) { this.roomCategory = roomCategory; }

    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
}
