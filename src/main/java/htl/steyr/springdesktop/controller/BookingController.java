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

    @FXML
    public void initialize() {
        customerComboBox.getItems().addAll(customerRepository.findAll());
        refreshBookingsList();
    }

    @FXML
    public void searchAvailableRooms() {
        LocalDate arrival = arrivalDatePicker.getValue();
        LocalDate departure = departureDatePicker.getValue();
        List<Room> availableRooms = roomRepository.findAll().stream()
                .filter(room -> isRoomAvailable(room, arrival, departure))
                .collect(Collectors.toList());
        availableRoomsListView.getItems().clear();
        availableRoomsListView.getItems().addAll(availableRooms);
    }

    @FXML
    public void addRoomToBooking() {
        Room selectedRoom = availableRoomsListView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            selectedRoomsListView.getItems().add(selectedRoom);
            availableRoomsListView.getItems().remove(selectedRoom);
            updateTotalPrice();
        }
    }

    @FXML
    public void removeRoomFromBooking() {
        Room selectedRoom = selectedRoomsListView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            availableRoomsListView.getItems().add(selectedRoom);
            selectedRoomsListView.getItems().remove(selectedRoom);
            updateTotalPrice();
        }
    }

    @FXML
    public void createBooking() {
        Customer customer = customerComboBox.getValue();
        LocalDate arrival = arrivalDatePicker.getValue();
        LocalDate departure = departureDatePicker.getValue();
        List<Room> selectedRooms = selectedRoomsListView.getItems();

        if (customer != null && arrival != null && departure != null && !selectedRooms.isEmpty()) {
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
    }

    @FXML
    public void cancelBooking() {
        Booking selectedBooking = bookingsListView.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            bookingRepository.delete(selectedBooking);
            refreshBookingsList();
        }
    }

    private boolean isRoomAvailable(Room room, LocalDate arrival, LocalDate departure) {
        List<RoomBooking> bookings = roomBookingRepository.findByRoom(room);
        return bookings.stream().noneMatch(booking ->
                (arrival.isBefore(booking.getBooking().getDateOfDeparture()) || arrival.isEqual(booking.getBooking().getDateOfDeparture())) &&
                        (departure.isAfter(booking.getBooking().getDateOfArrival()) || departure.isEqual(booking.getBooking().getDateOfArrival()))
        );
    }

    private void updateTotalPrice() {
        double totalPrice = selectedRoomsListView.getItems().stream()
                .mapToDouble(room -> room.getDailyRate().doubleValue())
                .sum();
        totalPriceField.setText(String.format("%.2f", totalPrice));
    }

    private void refreshBookingsList() {
        bookingsListView.getItems().clear();
        bookingsListView.getItems().addAll(bookingRepository.findAll());
    }

    private void clearFields() {
        arrivalDatePicker.setValue(null);
        departureDatePicker.setValue(null);
        customerComboBox.getSelectionModel().clearSelection();
        availableRoomsListView.getItems().clear();
        selectedRoomsListView.getItems().clear();
        totalPriceField.clear();
    }
}
