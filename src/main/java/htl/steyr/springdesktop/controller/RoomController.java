package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.Notification;
import htl.steyr.springdesktop.model.Room;
import htl.steyr.springdesktop.model.RoomCategory;
import htl.steyr.springdesktop.model.RoomType;
import htl.steyr.springdesktop.repository.RoomRepository;
import htl.steyr.springdesktop.repository.RoomCategoryRepository;
import htl.steyr.springdesktop.repository.RoomTypeRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Controller for managing rooms.
 * Provides functionality to add, edit, delete, and display rooms.
 */
@Component
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomCategoryRepository roomCategoryRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @FXML
    private ListView<Room> roomListView;
    @FXML
    private TextField roomNumberField;
    @FXML
    private TextField dailyRateField;
    @FXML
    private ComboBox<RoomCategory> categoryComboBox;
    @FXML
    private ComboBox<RoomType> typeComboBox;

    private Notification notification = new Notification();

    /**
     * Initializes the controller by populating the room list and dropdown menus.
     */
    @FXML
    public void initialize() {
        refreshRoomList();
        categoryComboBox.getItems().addAll(roomCategoryRepository.findAll());
        typeComboBox.getItems().addAll(roomTypeRepository.findAll());
    }

    /**
     * Adds a new room to the database.
     * Retrieves input values, creates a new Room object, and saves it.
     * Ensures all fields are filled and valid before saving.
     */
    @FXML
    public void addRoom() {
        try {
            if (roomNumberField.getText().isEmpty() || dailyRateField.getText().isEmpty() ||
                    categoryComboBox.getValue() == null || typeComboBox.getValue() == null) {
                notification.showError("Error", "Missing Fields", "All fields must be filled!");
                return;
            }

            Room room = new Room();
            room.setRoomNumber(Integer.parseInt(roomNumberField.getText()));
            room.setDailyRate(new BigDecimal(dailyRateField.getText()));
            room.setRoomCategory(categoryComboBox.getValue());
            room.setRoomType(typeComboBox.getValue());

            roomRepository.save(room);
            refreshRoomList();
            clearFields();
        } catch (NumberFormatException e) {
            notification.showError("Error", "Invalid Input", "Room number and daily rate must be valid numbers!");
        }
    }

    /**
     * Edits the selected room.
     * Loads the selected room's data into the input fields for modification.
     */
    @FXML
    public void editRoom() {
        Room selectedRoom = roomListView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            roomNumberField.setText(String.valueOf(selectedRoom.getRoomNumber()));
            dailyRateField.setText(String.valueOf(selectedRoom.getDailyRate()));
            categoryComboBox.setValue(selectedRoom.getRoomCategory());
            typeComboBox.setValue(selectedRoom.getRoomType());
        } else {
            notification.showError("Error", "No Room Selected", "Please select a room to edit!");
        }
    }

    /**
     * Deletes the selected room from the database.
     * If a room is selected, it removes it and updates the UI.
     */
    @FXML
    public void deleteRoom() {
        Room selectedRoom = roomListView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            roomRepository.delete(selectedRoom);
            refreshRoomList();
            clearFields();
        } else {
            notification.showError("Error", "No Room Selected", "Please select a room to delete!");
        }
    }

    /**
     * Refreshes the room list displayed in the UI.
     * Fetches all rooms from the database and updates the ListView.
     */
    private void refreshRoomList() {
        roomListView.getItems().clear();
        roomListView.getItems().addAll(roomRepository.findAll());
    }

    /**
     * Clears all input fields.
     */
    private void clearFields() {
        roomNumberField.clear();
        dailyRateField.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        typeComboBox.getSelectionModel().clearSelection();
    }
}
