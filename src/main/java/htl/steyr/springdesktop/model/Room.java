package htl.steyr.springdesktop.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity // Students are saved in a database table
@Table(name = "room") // optional, if not existing, the database table is named like the class
public class Room {
    @Id // mark field as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key is auto incremented
    @Column(name = "id", nullable = false)
    private Long id;    // Rapper Class -> id - null

    @Column(name = "room_number", nullable = false)
    private int roomNumber;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<RoomBooking> roomBookings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_category_id")
    private RoomCategory roomCategory;

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Room: " + roomNumber;
    }
}
