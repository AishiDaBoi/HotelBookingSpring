package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.JavaFxApplication;
import htl.steyr.springdesktop.model.Notification;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class StartController {

    Notification notification = new Notification();



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
    @FXML
    private void openCustomerManagement(ActionEvent event) {
        try {
            openWindow(getClass().getResource("customer-view.fxml"), true, false);
        } catch (IOException e) {
            e.printStackTrace();
            notification.showError("Window couldn't open", "Error", e.getMessage());
        }
    }

    @FXML
    private void openRoomManagement(ActionEvent event) {
        try {
            openWindow(getClass().getResource("room-view.fxml"), true, false);
        } catch (IOException e) {
            e.printStackTrace();
            notification.showError("Window couldn't open", "Error", e.getMessage());
        }
    }

    @FXML
    private void openRoomCategoryManagement(ActionEvent event) {
        try {
            openWindow(getClass().getResource("room-category-view.fxml"), true, false);
        } catch (IOException e) {
            e.printStackTrace();
            notification.showError("Window couldn't open", "Error", e.getMessage());
        }
    }

    @FXML
    private void openRoomTypeManagement(ActionEvent event) {
        try {
            openWindow(getClass().getResource("room-type-view.fxml"), true, false);
        } catch (IOException e) {
            e.printStackTrace();
            notification.showError("Window couldn't open", "Error", e.getMessage());
        }
    }

    @FXML
    private void openBookingManagement(ActionEvent event) {
        try {
            openWindow(getClass().getResource("booking-view.fxml"), true, false);
        } catch (IOException e) {
            e.printStackTrace();
            notification.showError("Window couldn't open", "Error", e.getMessage());
        }
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        Platform.exit();
    }
}
