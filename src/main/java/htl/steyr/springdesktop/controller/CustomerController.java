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

/**
 * Controller for managing customers.
 * Provides functionality to add, edit, and delete customers.
 */
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

    private final Notification notification = new Notification();

    /**
     * Initializes the customer list.
     */
    @FXML
    public void initialize() {
        refreshCustomerList();
    }

    /**
     * Adds a new customer.
     * Displays an error message if fields are empty or the birthdate is invalid.
     */
    @FXML
    public void addCustomer() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || birthdateField.getText().isEmpty() || phoneField.getText().isEmpty()) {
            notification.showError("Error", "Missing Fields", "Please fill in all fields before adding a customer.");
            return;
        }

        try {
            Customer customer = new Customer();
            customer.setFirstname(firstNameField.getText());
            customer.setLastname(lastNameField.getText());
            customer.setBirthdate(LocalDate.parse(birthdateField.getText()));
            customer.setPhone(phoneField.getText());
            customerRepository.save(customer);
            refreshCustomerList();
            clearFields();
        } catch (DateTimeParseException e) {
            notification.showError("Error", "Invalid Date", "Please enter a valid date in YYYY-MM-DD format.");
        }
    }

    /**
     * Edits the selected customer.
     * Displays an error message if no customer is selected or fields are empty.
     */
    @FXML
    public void editCustomer() {
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            notification.showError("Error", "No Customer Selected", "Please select a customer to edit.");
            return;
        }

        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || birthdateField.getText().isEmpty() || phoneField.getText().isEmpty()) {
            notification.showError("Error", "Missing Fields", "Please fill in all fields before editing.");
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
            notification.showError("Error", "Invalid Date", "Please enter a valid date in YYYY-MM-DD format.");
        }
    }

    /**
     * Deletes the selected customer.
     * Displays an error message if no customer is selected.
     */
    @FXML
    public void deleteCustomer() {
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            notification.showError("Error", "No Customer Selected", "Please select a customer to delete.");
            return;
        }

        customerRepository.delete(selectedCustomer);
        refreshCustomerList();
        clearFields();
    }

    /**
     * Refreshes the customer list.
     */
    private void refreshCustomerList() {
        customerListView.getItems().clear();
        customerListView.getItems().addAll(customerRepository.findAll());
    }

    /**
     * Clears all input fields.
     */
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        birthdateField.clear();
        phoneField.clear();
    }
}
