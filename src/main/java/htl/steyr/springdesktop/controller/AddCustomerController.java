package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.Customer;
import htl.steyr.springdesktop.model.Notification;
import htl.steyr.springdesktop.repository.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@Component
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

    Notification notification = new Notification();

    // Methode zum Schließen des Fensters
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeBTN.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveCustomer(ActionEvent event) {

        String firstname = firstnameTFD.getText();
        String lastname = lastnameTFD.getText();
        LocalDate dateOfBirth = dateDP.getValue();
        String phone = phoneTFD.getText();

        // Überprüfen, ob alle Felder ausgefüllt sind
        if (firstname.isEmpty() || lastname.isEmpty() || dateOfBirth == null || phone.isEmpty()) {
            notification.showError("Fehler", "Fehler beim Speichern", "Bitte füllen Sie alle Felder aus!");
            return;
        }

        // Neuer Customer erstellen
        Customer newCustomer = new Customer();
        newCustomer.setFirstname(firstname);
        newCustomer.setLastname(lastname);
        newCustomer.setPhone(phone);
        newCustomer.setBirthdate(dateOfBirth.toString());  // oder als LocalDate speichern

       customerRepository.save(newCustomer);


        // Erfolgreiche Speicherung und Schließen des Fensters
        closeWindow(event);
    }


}
