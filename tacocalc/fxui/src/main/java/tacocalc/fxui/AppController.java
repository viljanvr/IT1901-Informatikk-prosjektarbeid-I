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
    private TextField ingredientNameField, ingredientAmntField;

    @FXML
    private Button addIngredient;

    @FXML
    private Button editButton;

    @FXML
    private Button nameButton;

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
        List<Node> z = ingredientsList.getChildren().stream().filter(n -> (n.equals(c)
                || n.equals(d)
                // TODO: Change "contains" so you can't remove duplicates at the same time
                || (n instanceof TextField && ((TextField) n).getText().contains(ingredient))))
                .collect(Collectors.toList());
        for (Node n : z) {
            ingredientsList.getChildren().remove(n);
        }
    }

    private void handleSaveToFile(String name){
        shoppingList.write(name);
    }

    @FXML
    private void handleAddIngredient() {
        try {
            String ingredientName = ingredientNameField.getText();
            String ingredientAmnt = ingredientAmntField.getText();
            String name = nameButton.getText();

            shoppingList.addItem(ingredientName, Integer.parseInt(ingredientAmnt));

            //Saving to file after shoppinglist has added the new thing

            CheckBox c = new CheckBox();
            Button deleteButton = new Button("Delete");
            deleteButton.setVisible(editMode);
            TextField t = new TextField(ingredientAmnt + "x " + ingredientName);
            t.setEditable(false);

            // Event handler for delete button
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    handleDelete(ingredientName, c, deleteButton);
                    handleSaveToFile(name);
                }
            });

            // Event handler for checkbox
            c.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    shoppingList.setBought(ingredientName, ((CheckBox) e.getSource()).isSelected());
                    System.out.println(shoppingList.toString());
                    handleSaveToFile(name);
                }
            });

            ingredientsList.addRow(ingredientsList.getRowCount(), c, t, deleteButton);
            
            //Save to file
            handleSaveToFile(name);
            
            // Clear out input fields
            ingredientAmntField.clear();
            ingredientNameField.clear();

        } catch (Exception e) {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Amount needs to be a valid inteeger");
            a.show();
        }
    }
}