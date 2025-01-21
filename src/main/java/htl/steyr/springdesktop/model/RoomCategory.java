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
    private RoomCategories roomCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomCategory")
    private List<Room> rooms;



    public RoomCategories getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategories roomCategory) {
        this.roomCategory = roomCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
