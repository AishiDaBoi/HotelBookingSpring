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

    Notification notification = new Notification();

    @FXML
    public void initialize() {
        refreshRoomList();
        categoryComboBox.getItems().addAll(roomCategoryRepository.findAll());
        typeComboBox.getItems().addAll(roomTypeRepository.findAll());
    }

    @FXML
    public void addRoom() {
        Room room = new Room();
        room.setRoomNumber(Integer.parseInt(roomNumberField.getText()));
        room.setDailyRate(new BigDecimal(dailyRateField.getText()));
        room.setRoomCategory(categoryComboBox.getValue());
        room.setRoomType(typeComboBox.getValue());
        roomRepository.save(room);
        refreshRoomList();
        clearFields();
    }

    @FXML
    public void editRoom() {
        Room selectedRoom = roomListView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            roomNumberField.setText(String.valueOf(selectedRoom.getRoomNumber()));
            dailyRateField.setText(String.valueOf(selectedRoom.getDailyRate()));
            categoryComboBox.setValue(selectedRoom.getRoomCategory());
            typeComboBox.setValue(selectedRoom.getRoomType());
            refreshRoomList();
            clearFields();
        } else {
            notification.showError("Error", "Error", "No room selected!");
        }
    }

    @FXML
    public void deleteRoom() {
        Room selectedRoom = roomListView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            roomRepository.delete(selectedRoom);
            refreshRoomList();
            clearFields();
        }
    }

    private void refreshRoomList() {
        roomListView.getItems().clear();
        roomListView.getItems().addAll(roomRepository.findAll());
    }

    private void clearFields() {
        roomNumberField.clear();
        dailyRateField.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        typeComboBox.getSelectionModel().clearSelection();
    }
}
