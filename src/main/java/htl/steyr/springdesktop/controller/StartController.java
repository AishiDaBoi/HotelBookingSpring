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

        openWindow("booking-view.fxml", "Buchung"); // Korrekte FXML-Datei und Titel
    }


    private void openWindow(String fxmlFile, String windowTitle) {
        try {
            // Korrekt den Pfad zur FXML-Datei angeben
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/springdesktop/controller/" + fxmlFile));  // Pfad relativ zum resources-Verzeichnis
            AnchorPane root = loader.load();

            // Neues Fenster erstellen
            Stage stage = new Stage();
            stage.setTitle(windowTitle);
            stage.initModality(Modality.APPLICATION_MODAL); // Verhindert, dass der Benutzer zum Hauptfenster zurückkehrt, bevor er das neue Fenster schließt
            stage.initOwner(JavaFxApplication.primaryStage); // Referenz auf den Primär-Stage, falls verfügbar
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler beim Öffnen des Fensters: " + e.getMessage());
        }
    }




    public void openCustomerWindow(ActionEvent event) {

        openWindow("addCustomer-view.fxml", "Add Customer");

    }
}
