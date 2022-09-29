package tacocalc.fxui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import tacocalc.core.ShoppingList;

public class AppController {

    // Connects the main Shoppingslist class to the FXML file
    @FXML
    private GridPane ingredientsList;

    @FXML
    private TextField ingredientNameField, ingredientAmntField, nameField;

    @FXML
    private Button addIngredient;

    @FXML
    private Button editButton, loadButton;

    private Boolean editMode = false;

    private ShoppingList shoppingList = new ShoppingList();

    public void initialize() {
    }

    @FXML
    private void handleEditButton() {
        editMode = !editMode;
        ingredientsList.getChildren().stream().filter(a -> a instanceof Button).forEach(a -> a.setVisible(editMode));
        editButton.setText(editMode ? "Cancel" : "Edit");
    }

    private void handleDelete(String ingredient, CheckBox c, Button d) {

        shoppingList.deleteItem(ingredient); // delete from database
        List<Node> children = ingredientsList.getChildren().stream().filter(n -> (n.equals(c)
                || n.equals(d)
                // TODO: Change "contains" so you can't remove duplicates at the same time
                || (n instanceof TextField && ((TextField) n).getText().contains(ingredient))))
                .collect(Collectors.toList());
        for (Node n : children) {
            ingredientsList.getChildren().remove(n);
        }
        handleSaveToFile(getFileName());
    }

    private void handleToggleCheckbox(String ingredientName, CheckBox c) {
        shoppingList.setBought(ingredientName, c.isSelected());
        handleSaveToFile(getFileName());
    }

    private void handleSaveToFile(String name) {
        shoppingList.write(name);
    }

    @FXML
    private void handleLoadFile() {
        this.ingredientsList.getChildren().clear();
        this.shoppingList = shoppingList.read(getFileName());
        shoppingList.getList().stream().forEach(n -> addItemToView(n.getName(), n.getAmount(), n.getBought()));
    }

    @FXML
    private void handleAddIngredient() {
        try {
            String ingredientName = ingredientNameField.getText();
            Integer ingredientAmnt = Integer.parseInt(ingredientAmntField.getText());

            shoppingList.addItem(ingredientName, ingredientAmnt);
            handleSaveToFile(getFileName());

            addItemToView(ingredientName, ingredientAmnt, false);

        } catch (Exception e) {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Amount needs to be a valid integer");
            a.show();
        }
    }

    private void addItemToView(String ingredientName, Integer ingredientAmnt, Boolean checked) {

        CheckBox c = new CheckBox();
        c.setSelected(checked);

        Button deleteButton = new Button("Delete");
        deleteButton.setVisible(editMode);

        TextField t = new TextField(ingredientAmnt + "x " + ingredientName);
        t.setEditable(false);

        // Event handler for delete button
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleDelete(ingredientName, c, deleteButton);
            }
        });

        // Event handler for checkbox
        c.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                handleToggleCheckbox(ingredientName, (CheckBox) e.getSource());
            }
        });

        ingredientsList.addRow(ingredientsList.getRowCount(), c, t, deleteButton);

        // Clear out input fields
        ingredientAmntField.clear();
        ingredientNameField.clear();
    }

    private String getFileName() {
        // TODO: Give nameField a better name
        return nameField.getText();
    }
}