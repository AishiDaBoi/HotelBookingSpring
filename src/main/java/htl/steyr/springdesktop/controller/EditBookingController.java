package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.Customer;
import htl.steyr.springdesktop.model.RoomCategories;
import htl.steyr.springdesktop.model.RoomTypes;
import htl.steyr.springdesktop.repository.BookingRepository;
import htl.steyr.springdesktop.repository.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class EditBookingController implements Initializable {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;


    public Button bookingBTN;
    public Button addCustomerBTN;
    public ListView<Customer> customerLVW; // Typed ListView for better safety
    public DatePicker startdateDP;
    public DatePicker enddateDP;
    public Button saveBTN;
    public ComboBox<RoomTypes> roomTypeCBX; // Typed ComboBox for RoomTypes
    public ComboBox<RoomCategories> roomCategoryCBX; // Typed ComboBox for RoomCategories



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void saveBooking(ActionEvent event) {

    }

    private boolean validateInputs() {
      return true;
    }


}
