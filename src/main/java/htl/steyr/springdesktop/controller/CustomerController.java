package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.Customer;
import htl.steyr.springdesktop.model.Notification;
import htl.steyr.springdesktop.repository.CustomerRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @FXML
    private ListView<Customer> customerListView;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField birthdateField;
    @FXML
    private TextField phoneField;

    Notification notification = new Notification();

    @FXML
    public void initialize() {
        refreshCustomerList();
    }

    @FXML
    public void addCustomer() {
        Customer customer = new Customer();
        customer.setFirstname(firstNameField.getText());
        customer.setLastname(lastNameField.getText());
        customer.setBirthdate(LocalDate.parse(birthdateField.getText()));
        customer.setPhone(phoneField.getText());
        customerRepository.save(customer);
        refreshCustomerList();
        clearFields();
    }


    public void editCustomer() {
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            if (firstNameField.getText().isEmpty()
                    || lastNameField.getText().isEmpty()
                    || birthdateField.getText().isEmpty()
                    || phoneField.getText().isEmpty()) {

                notification.showError("Error", "Error", "Fill all fields!");
                return;
            }

            try {
                selectedCustomer.setFirstname(firstNameField.getText());
                selectedCustomer.setLastname(lastNameField.getText());
                selectedCustomer.setBirthdate(LocalDate.parse(birthdateField.getText()));
                selectedCustomer.setPhone(phoneField.getText());
                customerRepository.save(selectedCustomer);
                refreshCustomerList();
                clearFields();
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                notification.showError("Error", "Error", "Invalid date!");
            }
        }
    }



    @FXML
    public void deleteCustomer() {
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            customerRepository.delete(selectedCustomer);
            refreshCustomerList();
            clearFields();
        }
    }

    private void refreshCustomerList() {
        customerListView.getItems().clear();
        customerListView.getItems().addAll(customerRepository.findAll());
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        birthdateField.clear();
        phoneField.clear();
    }
}
