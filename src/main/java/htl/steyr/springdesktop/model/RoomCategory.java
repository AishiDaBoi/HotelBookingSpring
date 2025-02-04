package htl.steyr.springdesktop.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "room_category")
public class RoomCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "room_category", nullable = false)
    private String roomCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomCategory")
    private List<Room> rooms;

    @Override
    public String toString() {
        return this.roomCategory;
    }

    public RoomCategory() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
