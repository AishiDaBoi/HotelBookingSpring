package htl.steyr.springdesktop.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date_of_arrival", nullable = false)
    private LocalDate dateOfArrival;

    @Column(name = "date_of_departure", nullable = false)
    private LocalDate dateOfDeparture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "booking")
    private List<RoomBooking> roomBookings;

   @Column(name = "roomtype")
   private RoomTypes roomType;

    @Column(name = "roomcategory")
    private RoomCategories roomCategory;

    public RoomTypes getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypes roomType) {
        this.roomType = roomType;
    }

    public RoomCategories getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategories roomCategory) {
        this.roomCategory = roomCategory;
    }




    /**
     * Important:
     * Sprint Boot needs a default constructor!
     * When we create a constructor that takes parameters,
     * we have to define manually an empty default constructor!
     */
    public Booking() {}

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(LocalDate dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Booking) {
            return this.getId().equals(((Booking) obj).getId());
        }

        return false;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<RoomBooking> getRoomBookings() {
        return roomBookings;
    }

    public void setRoomBookings(List<RoomBooking> roomBookings) {
        this.roomBookings = roomBookings;
    }


}
