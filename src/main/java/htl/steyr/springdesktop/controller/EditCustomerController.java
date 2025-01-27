package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.Customer;
import htl.steyr.springdesktop.repository.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;

        if (customer != null) {
            firstnameTFD.setText(customer.getFirstname());
            lastnameTFD.setText(customer.getLastname());
            dateDP.setValue(LocalDate.parse(customer.getBirthdate()));
            phoneTFD.setText(customer.getPhone());
        }

    }


    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeBTN.getScene().getWindow();
        stage.close();
    }

    public void saveCustomer(ActionEvent event) {
        customer.setFirstname(firstnameTFD.getText());
        customer.setLastname(lastnameTFD.getText());
        customer.setBirthdate(dateDP.getValue().toString());
        customer.setPhone(phoneTFD.getText());
        customerRepository.save(customer);
        closeWindow(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}