package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.Booking;
import htl.steyr.springdesktop.model.Customer;
import htl.steyr.springdesktop.model.RoomCategories;
import htl.steyr.springdesktop.model.RoomTypes;
import htl.steyr.springdesktop.repository.BookingRepository;
import htl.steyr.springdesktop.repository.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddBookingController implements Initializable {
    public Button bookingBTN;
    public Button addCustomerBTN;
    public ListView<Customer> customerLVW; // Typed ListView for better safety
    public DatePicker startdateDP;
    public DatePicker enddateDP;
    public Button saveBTN;
    public ComboBox<RoomTypes> roomTypeCBX; // Typed ComboBox for RoomTypes
    public ComboBox<RoomCategories> roomCategoryCBX; // Typed ComboBox for RoomCategories

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load customers into the ListView
        customerLVW.getItems().clear();
        customerLVW.getItems().addAll(customerRepository.findAll());

        // Populate RoomTypes and RoomCategories ComboBoxes
        roomTypeCBX.getItems().setAll(RoomTypes.values());
        roomCategoryCBX.getItems().setAll(RoomCategories.values());
    }

    public void saveBooking(ActionEvent event) {
        // Validate inputs
        if (!validateInputs()) {
            showAlert(AlertType.ERROR, "Invalid Input", "Please ensure all fields are filled correctly.");
            return;
        }

        try {
            // Create and populate a Booking object
            Booking booking = new Booking();
            booking.setCustomer(customerLVW.getSelectionModel().getSelectedItem());
            booking.setDateOfArrival(startdateDP.getValue());
            booking.setDateOfDeparture(enddateDP.getValue());


            // Save the booking to the database
            bookingRepository.save(booking);

            // Provide feedback to the user
            showAlert(AlertType.INFORMATION, "Success", "Booking saved successfully!");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Failed to save booking: " + e.getMessage());
        }
    }

    private boolean validateInputs() {
        // Validate that all inputs are selected or filled
        return customerLVW.getSelectionModel().getSelectedItem() != null &&
                startdateDP.getValue() != null &&
                enddateDP.getValue() != null &&
                roomTypeCBX.getSelectionModel().getSelectedItem() != null &&
                roomCategoryCBX.getSelectionModel().getSelectedItem() != null &&
                startdateDP.getValue().isBefore(enddateDP.getValue());
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
