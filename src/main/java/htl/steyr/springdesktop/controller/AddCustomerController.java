package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.Customer;
import htl.steyr.springdesktop.repository.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
public class AddCustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @FXML
    private TextField firstnameTFD;
    @FXML
    private TextField lastnameTFD;
    @FXML
    private DatePicker dateDP;
    @FXML
    private TextField phoneTFD;
    @FXML
    private Button closeBTN;

    // Method to close the window
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeBTN.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveCustomer(ActionEvent event) {
        String firstname = firstnameTFD.getText().trim();
        String lastname = lastnameTFD.getText().trim();
        LocalDate dateOfBirth = dateDP.getValue();
        String phone = phoneTFD.getText().trim();

        // Validate fields
        if (firstname.isBlank() || lastname.isBlank() || dateOfBirth == null || phone.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Fehler", "Fehler beim Speichern", "Bitte füllen Sie alle Felder aus!");
            return;
        }

        // Create new customer
        Customer newCustomer = new Customer();
        newCustomer.setFirstname(firstname);
        newCustomer.setLastname(lastname);
        newCustomer.setPhone(phone);
        newCustomer.setBirthdate(dateOfBirth);  // Assuming birthdate is LocalDate in Customer entity

        customerRepository.save(newCustomer);

        // Show success message
        showAlert(Alert.AlertType.INFORMATION, "Erfolg", "Kunde gespeichert", "Der Kunde wurde erfolgreich gespeichert!");

        // Close window after saving
        closeWindow(event);
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
