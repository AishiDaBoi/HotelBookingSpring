package htl.steyr.springdesktop.controller;

import htl.steyr.springdesktop.model.RoomCategory;
import htl.steyr.springdesktop.repository.RoomCategoryRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for managing room categories.
 * Provides functionality to add, edit, delete, and display room categories.
 */
@Component
public class RoomCategoryController {

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    @FXML
    private ListView<RoomCategory> categoryListView;
    @FXML
    private TextField categoryNameField;

    /**
     * Initializes the controller by refreshing the room category list.
     */
    @FXML
    public void initialize() {
        refreshCategoryList();
    }

    /**
     * Adds a new room category to the database.
     * Retrieves the input value, creates a new RoomCategory object, and saves it.
     * After saving, refreshes the category list and clears the input field.
     */
    @FXML
    public void addCategory() {
        if (!categoryNameField.getText().isEmpty()) {
            RoomCategory category = new RoomCategory();
            category.setRoomCategory(categoryNameField.getText());
            roomCategoryRepository.save(category);
            refreshCategoryList();
            clearFields();
        }
    }

    /**
     * Edits the selected room category.
     * Updates the category name and saves the changes to the database.
     * If no category is selected, the method does nothing.
     */
    @FXML
    public void editCategory() {
        RoomCategory selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null && !categoryNameField.getText().isEmpty()) {
            selectedCategory.setRoomCategory(categoryNameField.getText());
            roomCategoryRepository.save(selectedCategory);
            refreshCategoryList();
            clearFields();
        }
    }

    /**
     * Deletes the selected room category from the database.
     * If a category is selected, it removes it and updates the UI.
     */
    @FXML
    public void deleteCategory() {
        RoomCategory selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            roomCategoryRepository.delete(selectedCategory);
            refreshCategoryList();
            clearFields();
        }
    }

    /**
     * Refreshes the room category list displayed in the UI.
     * Fetches all room categories from the database and updates the ListView.
     */
    private void refreshCategoryList() {
        categoryListView.getItems().clear();
        categoryListView.getItems().addAll(roomCategoryRepository.findAll());
    }

    /**
     * Clears the input field for category names.
     */
    private void clearFields() {
        categoryNameField.clear();
    }
}
