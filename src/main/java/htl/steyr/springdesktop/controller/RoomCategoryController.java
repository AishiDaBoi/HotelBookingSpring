package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.RoomCategory;
import htl.steyr.springdesktop.repository.RoomCategoryRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomCategoryController {

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    @FXML
    private ListView<RoomCategory> categoryListView;
    @FXML
    private TextField categoryNameField;

    @FXML
    public void initialize() {
        refreshCategoryList();
    }

    @FXML
    public void addCategory() {
        RoomCategory category = new RoomCategory();
        category.setRoomCategory(categoryNameField.getText());
        roomCategoryRepository.save(category);
        refreshCategoryList();
        clearFields();
    }

    @FXML
    public void editCategory() {
        RoomCategory selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            selectedCategory.setRoomCategory(categoryNameField.getText());
            roomCategoryRepository.save(selectedCategory);
            refreshCategoryList();
            clearFields();
        }
    }

    @FXML
    public void deleteCategory() {
        RoomCategory selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            roomCategoryRepository.delete(selectedCategory);
            refreshCategoryList();
            clearFields();
        }
    }

    private void refreshCategoryList() {
        categoryListView.getItems().clear();
        categoryListView.getItems().addAll(roomCategoryRepository.findAll());
    }

    private void clearFields() {
        categoryNameField.clear();
    }
}
