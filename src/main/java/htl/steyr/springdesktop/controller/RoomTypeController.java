package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.Notification;
import htl.steyr.springdesktop.model.RoomType;
import htl.steyr.springdesktop.repository.RoomTypeRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for managing room types.
 * Provides functionality to add, edit, delete, and display room types.
 */
@Component
public class RoomTypeController {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @FXML
    private ListView<RoomType> typeListView;
    @FXML
    private TextField typeNameField;

    private final Notification notification = new Notification();

    /**
     * Initializes the controller by populating the room type list.
     */
    @FXML
    public void initialize() {
        refreshTypeList();
    }

    /**
     * Adds a new room type to the database.
     * Displays an error if the name field is empty.
     */
    @FXML
    public void addType() {
        if (typeNameField.getText().isEmpty()) {
            notification.showError("Error", "Name Field is empty!", "You need to write a name into the field!");
            return;
        }

        RoomType type = new RoomType();
        type.setRoomType(typeNameField.getText());
        roomTypeRepository.save(type);
        refreshTypeList();
        clearFields();
    }

    /**
     * Edits the selected room type.
     * Displays an error if no type is selected or the name field is empty.
     */
    @FXML
    public void editType() {
        RoomType selectedType = typeListView.getSelectionModel().getSelectedItem();
        if (selectedType == null) {
            notification.showError("Error", "No room type selected!", "Please select a room type to edit.");
            return;
        }

        if (typeNameField.getText().isEmpty()) {
            notification.showError("Error", "Name Field is empty!", "You need to write a name into the field!");
            return;
        }

        selectedType.setRoomType(typeNameField.getText());
        roomTypeRepository.save(selectedType);
        refreshTypeList();
        clearFields();
    }

    /**
     * Deletes the selected room type from the database.
     * Displays an error if no type is selected.
     */
    @FXML
    public void deleteType() {
        RoomType selectedType = typeListView.getSelectionModel().getSelectedItem();
        if (selectedType == null) {
            notification.showError("Error", "No room type selected!", "Please select a room type to delete.");
            return;
        }

        roomTypeRepository.delete(selectedType);
        refreshTypeList();
        clearFields();
    }

    /**
     * Refreshes the room type list displayed in the UI.
     */
    private void refreshTypeList() {
        typeListView.getItems().clear();
        typeListView.getItems().addAll(roomTypeRepository.findAll());
    }

    /**
     * Clears all input fields.
     */
    private void clearFields() {
        typeNameField.clear();
    }
}
