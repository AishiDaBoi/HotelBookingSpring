package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.*;
import htl.steyr.springdesktop.repository.BookingRepository;
import htl.steyr.springdesktop.repository.CustomerRepository;
import htl.steyr.springdesktop.repository.RoomRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ListView<Customer> customerListView;

    @FXML
    private ListView<Room> availableRoomsListView;

    @FXML
    private ListView<Room> selectedRoomsListView;

    @FXML
    private Button saveBookingBtn;

    private final List<Room> selectedRooms = new ArrayList<>();

    @FXML
    public void initialize() {
        loadCustomers();
        availableRoomsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void loadCustomers() {
        customerListView.setItems(FXCollections.observableArrayList(customerRepository.findAll()));
    }

    @FXML
    public void loadAvailableRooms() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate != null && endDate != null && startDate.isBefore(endDate)) {
            List<Room> availableRooms = roomRepository.findAvailableRooms(startDate, endDate);
            availableRoomsListView.setItems(FXCollections.observableArrayList(availableRooms));
        } else {
            showError("Fehler", "Bitte gültiges Start- und Enddatum wählen.");
        }
    }

    @FXML
    public void selectRooms() {
        selectedRooms.clear();
        selectedRooms.addAll(availableRoomsListView.getSelectionModel().getSelectedItems());
        selectedRoomsListView.setItems(FXCollections.observableArrayList(selectedRooms));
    }

    @FXML
    public void saveBooking() {
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (selectedCustomer == null || selectedRooms.isEmpty() || startDate == null || endDate == null) {
            showError("Fehler", "Bitte Kunde, Zimmer und gültiges Datum auswählen.");
            return;
        }

        Booking booking = new Booking();
        booking.setCustomer(selectedCustomer);
        booking.setDateOfArrival(startDate);
        booking.setDateOfDeparture(endDate);

        List<RoomBooking> roomBookings = new ArrayList<>();
        for (Room room : selectedRooms) {
            RoomBooking roomBooking = new RoomBooking();
            roomBooking.setRoom(room);
            roomBooking.setBooking(booking);
            roomBookings.add(roomBooking);
        }
        booking.setRoomBookings(roomBookings);

        bookingRepository.save(booking);
        showSuccess("Buchung erfolgreich", "Die Buchung wurde gespeichert.");
    }

    @FXML
    public void calculateBookingPrice() {
        Booking booking = new Booking();
        booking.setRoomBookings(selectedRooms.stream().map(room -> {
            RoomBooking rb = new RoomBooking();
            rb.setRoom(room);
            rb.setBooking(booking);
            return rb;
        }).toList());
        booking.setDateOfArrival(startDatePicker.getValue());
        booking.setDateOfDeparture(endDatePicker.getValue());

        BigDecimal price = booking.calculateTotalCost();
        showSuccess("Buchungskosten", "Gesamtkosten: " + price + " €");
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
