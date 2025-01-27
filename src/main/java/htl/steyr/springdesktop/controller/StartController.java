package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.JavaFxApplication;
import htl.steyr.springdesktop.model.Booking;
import htl.steyr.springdesktop.model.Customer;
import htl.steyr.springdesktop.model.Notification;
import htl.steyr.springdesktop.repository.BookingRepository;
import htl.steyr.springdesktop.repository.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;

import static htl.steyr.springdesktop.JavaFxApplication.primaryStage;

@Component
public class StartController {

    public Button addCustomerBTN;
    public ListView<Booking> bookingsLVW;
    @FXML
    private ListView<Customer> customerListView;

    Notification notification = new Notification();

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private BookingRepository bookingRepository;

    @FXML
    public void initialize() {
        customerListView.getItems().clear();
        customerListView.getItems().addAll(customerRepo.findAll());

        bookingsLVW.getItems().clear();
        bookingsLVW.getItems().addAll(bookingRepository.findAll());
    }

    public void openAddCustomerWindow(ActionEvent event) {

        try {
            openWindow(StartController.class.getResource("addCustomer-view.fxml"), true, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void openEditCustomerWindow(ActionEvent event) {

        Customer customer = customerListView.getSelectionModel().getSelectedItem();
        if (customer != null) {
            try {
                EditCustomerController controller = (EditCustomerController) openWindow(StartController.class.getResource("editCustomer-view.fxml"), true, false);
                controller.setCustomer(customer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            notification.showError("Error", "Please select a customer to edit", "No customer selected");
        }



    }

    public void deleteCustomer(ActionEvent event) {
        Customer customer = customerListView.getSelectionModel().getSelectedItem();
        if (customer != null) {
            customerRepo.delete(customer);
            customerListView.getItems().remove(customer);
        } else {
            notification.showError("Error", "Please select a customer to delete", "No customer selected");
        }
    }

    public static Object openWindow(URL url, boolean modal, boolean resizeable) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setControllerFactory(JavaFxApplication.springContext::getBean);

        Parent root = fxmlLoader.load();

        Object controller = fxmlLoader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(resizeable);

        if (modal) {
            stage.initOwner(JavaFxApplication.primaryStage);
            stage.initModality(Modality.APPLICATION_MODAL);
        }

        stage.show();
        return controller;
    }

    public void openAddBookingWindow(ActionEvent event) {
        try {
            openWindow(StartController.class.getResource("add-booking-view.fxml"), true, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openEditBookingWindow(ActionEvent event) {
        try {
            openWindow(StartController.class.getResource("edit-add-booking-view.fxml"), true, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBooking(ActionEvent event) {
        Booking booking = bookingsLVW.getSelectionModel().getSelectedItem();
        if (booking != null) {
            bookingRepository.delete(booking);
            bookingsLVW.getItems().remove(booking);
        } else {
            notification.showError("Error", "Please select a booking to delete", "No booking selected");
        }
    }



    public void refreshLVWs(ActionEvent event) {

        customerListView.getItems().clear();
        customerListView.getItems().addAll(customerRepo.findAll());

        bookingsLVW.getItems().clear();
        bookingsLVW.getItems().addAll(bookingRepository.findAll());

    }
}
