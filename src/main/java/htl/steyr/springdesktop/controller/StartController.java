package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.JavaFxApplication;
import htl.steyr.springdesktop.model.Customer;
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
    @FXML
    private ListView<Customer> customerListView;

    @Autowired
    private CustomerRepository customerRepo;

    @FXML
    public void initialize() {
        // Lädt die Kundenliste beim Starten der Ansicht
        customerListView.getItems().addAll(customerRepo.findAll());
    }

    @FXML
    private void onBookingClick(MouseEvent event) {

     //   openWindow("booking-view.fxml", "Buchung"); // Korrekte FXML-Datei und Titel
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



    public void openCustomerWindow(ActionEvent event) {

        try {
            openWindow(StartController.class.getResource("addCustomer-view.fxml"), true, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
