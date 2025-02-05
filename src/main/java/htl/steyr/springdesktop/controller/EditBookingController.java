package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.Booking;
import htl.steyr.springdesktop.model.Customer;
import htl.steyr.springdesktop.model.RoomCategory;
import htl.steyr.springdesktop.model.RoomType;
import htl.steyr.springdesktop.repository.BookingRepository;
import htl.steyr.springdesktop.repository.CustomerRepository;
import htl.steyr.springdesktop.repository.RoomCategoryRepository;
import htl.steyr.springdesktop.repository.RoomTypeRepository;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class EditBookingController implements Initializable {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    public Button bookingBTN;
    public Button addCustomerBTN;
    public ListView<Customer> customerLVW; // Typed ListView for better safety
    public DatePicker startdateDP;
    public DatePicker enddateDP;
    public Button saveBTN;
    public ComboBox<RoomType> roomTypeCBX; // Corrected type
    public ComboBox<RoomCategory> roomCategoryCBX; // Corrected type

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load customers into ListView
        customerLVW.getItems().clear();
        customerLVW.getItems().addAll(customerRepository.findAll());


         roomTypeCBX.getItems().setAll(roomTypeRepository.findAll());
         roomCategoryCBX.getItems().setAll(roomCategoryRepository.findAll());
    }

    public void saveBooking(ActionEvent event) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.ERROR, "Fehler", "Bitte füllen Sie alle Felder aus.");
            return;
        }

        try {
            // Create and populate a new Booking object
            Booking booking = new Booking();
            booking.setCustomer(customerLVW.getSelectionModel().getSelectedItem());
            booking.setDateOfArrival(startdateDP.getValue());
            booking.setDateOfDeparture(enddateDP.getValue());
            booking.setRoomType(roomTypeCBX.getSelectionModel().getSelectedItem());
            booking.setRoomCategory(roomCategoryCBX.getSelectionModel().getSelectedItem());

            // Save to the database
            bookingRepository.save(booking);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Erfolg", "Buchung erfolgreich gespeichert!");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Fehler", "Speicherung fehlgeschlagen: " + e.getMessage());
        }
    }

    private boolean validateInputs() {
        return customerLVW.getSelectionModel().getSelectedItem() != null &&
                startdateDP.getValue() != null &&
                enddateDP.getValue() != null &&
                roomTypeCBX.getSelectionModel().getSelectedItem() != null &&
                roomCategoryCBX.getSelectionModel().getSelectedItem() != null &&
                startdateDP.getValue().isBefore(enddateDP.getValue());
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
