package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.Room;
import htl.steyr.springdesktop.model.RoomCategory;
import htl.steyr.springdesktop.model.RoomType;
import htl.steyr.springdesktop.repository.RoomCategoryRepository;
import htl.steyr.springdesktop.repository.RoomRepository;
import htl.steyr.springdesktop.repository.RoomTypeRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @FXML
    private TextField roomNumberTF;

    @FXML
    private TextField priceTF;

    @FXML
    private ComboBox<RoomCategory> categoryCB;

    @FXML
    private ComboBox<RoomType> typeCB;

    @FXML
    private ListView<Room> roomListView;

    @FXML
    public void initialize() {
        loadRooms();
        loadCategoriesAndTypes();
    }

    private void loadRooms() {
        List<Room> rooms = roomRepository.findAll();
        roomListView.setItems(FXCollections.observableArrayList(rooms));
    }

    private void loadCategoriesAndTypes() {
        categoryCB.setItems(FXCollections.observableArrayList(roomCategoryRepository.findAll()));
        typeCB.setItems(FXCollections.observableArrayList(roomTypeRepository.findAll()));
    }

    @FXML
    public void saveRoom() {
        try {
            Room room = new Room();
            room.setRoomNumber(Integer.parseInt(roomNumberTF.getText()));
            room.setPrice(new BigDecimal(priceTF.getText()));
            room.setRoomCategory(categoryCB.getValue());
            room.setRoomType(typeCB.getValue());

            roomRepository.save(room);
            loadRooms();
        } catch (Exception e) {
            showError("Fehler beim Speichern", "Bitte alle Felder korrekt ausfüllen.");
        }
    }

    @FXML
    public void deleteRoom() {
        Room selectedRoom = roomListView.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            roomRepository.delete(selectedRoom);
            loadRooms();
        } else {
            showError("Fehler", "Bitte ein Zimmer auswählen.");
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
