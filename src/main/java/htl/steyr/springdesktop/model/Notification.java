package htl.steyr.springdesktop.model;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class Notification {
  private Alert alert;

  public void showInformation(String title, String header, String content) {
    alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }

  public void showError(String title, String header, String content) {
    alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }

    public void showWarning(String title, String header, String content) {
        alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showConfirmation(String title, String header, String content) {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showNone(String title, String header, String content) {
        alert = new Alert(AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }





}
