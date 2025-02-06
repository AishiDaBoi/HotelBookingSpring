package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.RoomType;
import htl.steyr.springdesktop.repository.RoomTypeRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomTypeController {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @FXML
    private ListView<RoomType> typeListView;
    @FXML
    private TextField typeNameField;

    @FXML
    public void initialize() {
        refreshTypeList();
    }

    @FXML
    public void addType() {
        RoomType type = new RoomType();
        type.setRoomType(typeNameField.getText());
        roomTypeRepository.save(type);
        refreshTypeList();
        clearFields();
    }

    @FXML
    public void editType() {
        RoomType selectedType = typeListView.getSelectionModel().getSelectedItem();
        if (selectedType != null) {
            selectedType.setRoomType(typeNameField.getText());
            roomTypeRepository.save(selectedType);
            refreshTypeList();
            clearFields();
        }
    }

    @FXML
    public void deleteType() {
        RoomType selectedType = typeListView.getSelectionModel().getSelectedItem();
        if (selectedType != null) {
            roomTypeRepository.delete(selectedType);
            refreshTypeList();
            clearFields();
        }
    }

    private void refreshTypeList() {
        typeListView.getItems().clear();
        typeListView.getItems().addAll(roomTypeRepository.findAll());
    }

    private void clearFields() {
        typeNameField.clear();
    }
}
