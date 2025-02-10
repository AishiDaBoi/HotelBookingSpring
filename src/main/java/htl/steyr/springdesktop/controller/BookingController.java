package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.*;
import htl.steyr.springdesktop.repository.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for handling room bookings.
 * Allows users to search available rooms, add rooms to a booking, and manage bookings.
 */
@Component
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @FXML
    private DatePicker arrivalDatePicker;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ListView<Room> availableRoomsListView;
    @FXML
    private ListView<Room> selectedRoomsListView;
    @FXML
    private TextField totalPriceField;
    @FXML
    private ListView<Booking> bookingsListView;

    private final Notification notification = new Notification();

    /**
     * Initializes the controller by populating the customer list and refreshing the bookings list.
     */
    @FXML
    public void initialize() {
        customerComboBox.getItems().addAll(customerRepository.findAll());
        refreshBookingsList();
    }

    /**
     * Searches for available rooms based on selected dates.
     * Displays an error message if no dates are selected.
     */
    @FXML
    public void searchAvailableRooms() {
        if (arrivalDatePicker.getValue() == null || departureDatePicker.getValue() == null) {
            notification.showError("Error", "Date Selection Required", "You must select an arrival and departure date.");
            return;
        }

        LocalDate arrival = arrivalDatePicker.getValue();
        LocalDate departure = departureDatePicker.getValue();
        List<Room> availableRooms = roomRepository.findAll().stream()
                .filter(room -> isRoomAvailable(room, arrival, departure))
                .collect(Collectors.toList());
        availableRoomsListView.getItems().clear();
        availableRoomsListView.getItems().addAll(availableRooms);
    }

    /**
     * Adds a selected room to the booking.
     * Displays an error if no room is selected.
     */
    @FXML
    public void addRoomToBooking() {
        Room selectedRoom = availableRoomsListView.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            notification.showError("Error", "No Room Selected", "Please select a room to add.");
            return;
        }

        selectedRoomsListView.getItems().add(selectedRoom);
        availableRoomsListView.getItems().remove(selectedRoom);
        updateTotalPrice();
    }

    /**
     * Removes a selected room from the booking.
     * Displays an error if no room is selected.
     */
    @FXML
    public void removeRoomFromBooking() {
        Room selectedRoom = selectedRoomsListView.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            notification.showError("Error", "No Room Selected", "Please select a room to remove.");
            return;
        }

        availableRoomsListView.getItems().add(selectedRoom);
        selectedRoomsListView.getItems().remove(selectedRoom);
        updateTotalPrice();
    }

    /**
     * Creates a new booking.
     * Displays an error if fields are missing.
     */
    @FXML
    public void createBooking() {
        Customer customer = customerComboBox.getValue();
        LocalDate arrival = arrivalDatePicker.getValue();
        LocalDate departure = departureDatePicker.getValue();
        List<Room> selectedRooms = selectedRoomsListView.getItems();

        if (customer == null || arrival == null || departure == null || selectedRooms.isEmpty()) {
            notification.showError("Error", "Incomplete Booking", "Please fill all fields and select at least one room.");
            return;
        }

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setDateOfArrival(arrival);
        booking.setDateOfDeparture(departure);
        bookingRepository.save(booking);

        for (Room room : selectedRooms) {
            RoomBooking roomBooking = new RoomBooking();
            roomBooking.setRoom(room);
            roomBooking.setBooking(booking);
            roomBookingRepository.save(roomBooking);
        }

        refreshBookingsList();
        clearFields();
    }

    /**
     * Cancels the selected booking.
     * Displays an error if no booking is selected.
     */
    @FXML
    public void cancelBooking() {
        Booking selectedBooking = bookingsListView.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            notification.showError("Error", "No Booking Selected", "Please select a booking to cancel.");
            return;
        }

        bookingRepository.delete(selectedBooking);
        refreshBookingsList();
    }

    /**
     * Checks if a room is available for the given date range.
     *
     * @param room     The room to check.
     * @param arrival  The arrival date.
     * @param departure The departure date.
     * @return true if the room is available, false otherwise.
     */
    private boolean isRoomAvailable(Room room, LocalDate arrival, LocalDate departure) {
        List<RoomBooking> bookings = roomBookingRepository.findByRoom(room);
        return bookings.stream().noneMatch(booking ->
                (arrival.isBefore(booking.getBooking().getDateOfDeparture()) || arrival.isEqual(booking.getBooking().getDateOfDeparture())) &&
                        (departure.isAfter(booking.getBooking().getDateOfArrival()) || departure.isEqual(booking.getBooking().getDateOfArrival()))
        );
    }

    /**
     * Updates the total price based on selected rooms.
     */
    private void updateTotalPrice() {
        double totalPrice = selectedRoomsListView.getItems().stream()
                .mapToDouble(room -> room.getDailyRate().doubleValue())
                .sum();
        totalPriceField.setText(String.format("%.2f", totalPrice));
    }

    /**
     * Refreshes the bookings list.
     */
    private void refreshBookingsList() {
        bookingsListView.getItems().clear();
        bookingsListView.getItems().addAll(bookingRepository.findAll());
    }

    /**
     * Clears all input fields.
     */
    private void clearFields() {
        arrivalDatePicker.setValue(null);
        departureDatePicker.setValue(null);
        customerComboBox.getSelectionModel().clearSelection();
        availableRoomsListView.getItems().clear();
        selectedRoomsListView.getItems().clear();
        totalPriceField.clear();
    }
}
