package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.JavaFxApplication;
import htl.steyr.springdesktop.model.Notification;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class StartController {

    private final Notification notification = new Notification();

    // Referenz auf den Wurzelcontainer aus der FXML
    @FXML
    private BorderPane rootPane;

    // Diese Animationen sollten in der FXML (z.B. via <fx:define>) definiert sein
    @FXML
    private FadeTransition fadeInTransition;

    @FXML
    private TranslateTransition slideDownTransition;

    /**
     * Öffnet ein neues Fenster.
     *
     * @param url         Die URL der FXML-Datei.
     * @param modal       true, wenn das Fenster modal sein soll.
     * @param resizeable  true, wenn das Fenster vergrößerbar sein soll.
     * @return Den Controller des geöffneten Fensters.
     * @throws IOException falls das Laden der FXML fehlschlägt.
     */
    public static Object openWindow(URL url, boolean modal, boolean resizeable) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        // Nutzt den Spring Context, um den Controller zu instanziieren
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
            openWindow(getClass().getResource("booking-view.fxml"), true, true);
        } catch (IOException e) {
            e.printStackTrace();
            notification.showError("Window couldn't open", "Error", e.getMessage());
        }
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Spielt den Einblend- und Slide-Down-Effekt ab.
     * Diese Methode wird via onShown-Event in der FXML aufgerufen.
     */
    @FXML
    private void playEntranceAnimation() {
        if (fadeInTransition != null) {
            fadeInTransition.play();
        }
        if (slideDownTransition != null) {
            slideDownTransition.play();
        }
    }

    /**
     * Skaliert das Element beim Mouse-Hover leicht nach oben.
     *
     * @param event Das MouseEvent.
     */
    @FXML
    private void scaleUp(MouseEvent event) {
        Node node = (Node) event.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
        st.setToX(1.1);
        st.setToY(1.1);
        st.play();
    }

    /**
     * Bringt das Element beim Mouse-Verlassen wieder auf Originalgröße.
     *
     * @param event Das MouseEvent.
     */
    @FXML
    private void scaleDown(MouseEvent event) {
        Node node = (Node) event.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
    }
}
