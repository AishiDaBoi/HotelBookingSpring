package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.Customer;
import htl.steyr.springdesktop.repository.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.lang.String;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
public class EditCustomerController implements Initializable {

    @Autowired
    private CustomerRepository customerRepository;

    public TextField firstnameTFD;
    public TextField lastnameTFD;
    public DatePicker dateDP;
    public TextField phoneTFD;
    public Button closeBTN;
    public Button saveBTN;
    private Customer customer; // Made private to avoid direct access

    public void setCustomer(Customer customer) {
        this.customer = customer;

        if (customer != null) {
            firstnameTFD.setText(customer.getFirstname());
            lastnameTFD.setText(customer.getLastname());

            if (customer.getBirthdate() != null) {
                dateDP.setValue((customer.getBirthdate()));
            }

            phoneTFD.setText(customer.getPhone());
        }
    }

    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeBTN.getScene().getWindow();
        stage.close();
    }

    public void saveCustomer(ActionEvent event) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.ERROR, "Fehler", "Bitte füllen Sie alle Felder korrekt aus!");
            return;
        }

        if (customer == null) {
            customer = new Customer();
        }

        customer.setFirstname(firstnameTFD.getText());
        customer.setLastname(lastnameTFD.getText());
        customer.setBirthdate(dateDP.getValue());
        customer.setPhone(phoneTFD.getText());

        customerRepository.save(customer);
        showAlert(Alert.AlertType.INFORMATION, "Erfolg", "Kunde erfolgreich gespeichert!");
        closeWindow(event);
    }

    private boolean validateInputs() {
        return firstnameTFD.getText() != null && !firstnameTFD.getText().isEmpty() &&
                lastnameTFD.getText() != null && !lastnameTFD.getText().isEmpty() &&
                dateDP.getValue() != null &&
                phoneTFD.getText() != null && !phoneTFD.getText().isEmpty();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // This can be used for future setup
    }
}
