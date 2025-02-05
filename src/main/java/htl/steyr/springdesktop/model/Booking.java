package htl.steyr.springdesktop.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "booking", cascade = CascadeType.ALL)
    private List<RoomBooking> roomBookings;

    public Booking() {}

    public BigDecimal calculateTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        long days = dateOfArrival.until(dateOfDeparture).getDays();

        if (days <= 0) return totalCost;

        for (RoomBooking roomBooking : roomBookings) {
            Room room = roomBooking.getRoom();
            totalCost = totalCost.add(room.getPrice().multiply(BigDecimal.valueOf(days)));
        }

        // 10% Rabatt ab 5 Zimmern
        if (roomBookings.size() >= 5) {
            totalCost = totalCost.multiply(BigDecimal.valueOf(0.9));
        }

        return totalCost;
    }

    // Getter und Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDateOfArrival() { return dateOfArrival; }
    public void setDateOfArrival(LocalDate dateOfArrival) { this.dateOfArrival = dateOfArrival; }

    public LocalDate getDateOfDeparture() { return dateOfDeparture; }
    public void setDateOfDeparture(LocalDate dateOfDeparture) { this.dateOfDeparture = dateOfDeparture; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<RoomBooking> getRoomBookings() { return roomBookings; }
    public void setRoomBookings(List<RoomBooking> roomBookings) { this.roomBookings = roomBookings; }
}
