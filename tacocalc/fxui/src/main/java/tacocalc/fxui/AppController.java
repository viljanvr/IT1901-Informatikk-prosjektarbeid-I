package tacocalc.fxui;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tacocalc.core.ShoppingList;

public class AppController {

  // Connects the main Shoppingslist class to the FXML file
  @FXML
  private GridPane ingredientsList, ingredientsList2;

  @FXML
  private BorderPane popUpContain;

  @FXML
  private TextField ingredientNameField, ingredientAmntField, nameField;

  @FXML
  private Button addIngredient, goBackButton;

  @FXML
  private Button editButton, loadButton;

  private Boolean editMode = false;

  private ShoppingList shoppingList = new ShoppingList();

  public void initialize() {
  }

  // Keeps track of left or right.
  private int counter = 0;

  @FXML
  private void handleEditButton() {
    editMode = !editMode;
    ingredientsList.getChildren().stream().filter(a -> a instanceof Button).forEach(a -> a.setVisible(editMode));
    ingredientsList2.getChildren().stream().filter(a -> a instanceof Button).forEach(a -> a.setVisible(editMode));
    editButton.setText(editMode ? "Cancel" : "Edit");
  }

  // TODO: This takes in the dropdown editors delete function instead of the
  // button. Must also change d to dropdown/popup
  private void handleDelete(String ingredient, CheckBox c, MenuButton d) {

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
    shoppingList.getList().stream().forEach(n -> {
      try {
        addItemToView(n.getName(), n.getAmount(), n.getBought());
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });
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

  @FXML
  public void handleGoBack() {
    popUpContain.setVisible(false);
  }

  private void buildEditPane(String ingredientName) throws IOException {
    popUpContain.setVisible(true);
    popUpContain.setCenter(loadPopUp());

    // TODO: The Controller needs to make a correct controller for the given
    // ingredientname
  }

  private Pane loadPopUp() throws IOException {
    try {
      Pane root = FXMLLoader.load(getClass().getResource("PopupMenu.fxml"));
      return root;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private void addItemToView(String ingredientName, Integer ingredientAmnt, Boolean checked) throws IOException {

    CheckBox c = new CheckBox();
    c.setSelected(checked);

    // TODO: Delete this
    Button deleteButton = new Button("->");
    deleteButton.setVisible(editMode);

    Button editButton = new Button("->");
    editButton.setVisible(editMode);

    TextField t = new TextField(ingredientAmnt + "x " + ingredientName);
    t.setEditable(false);

    // Event handler for delete button
    // TODO: Make this an edit button instead
    editButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        // TODO: Call a function for dropdown menu instead of delete
        try {
          buildEditPane(ingredientName);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    });

    // Event handler for checkbox
    c.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        handleToggleCheckbox(ingredientName, (CheckBox) e.getSource());
      }
    });

    if (counter == 0) {
      ingredientsList.addRow(ingredientsList.getRowCount(), c, t, editButton);
      counter = 1;
    } else {
      ingredientsList2.addRow(ingredientsList2.getRowCount(), c, t, editButton);
      counter = 0;
    }

    // TODO: Remove old solution
    /*
     * if(counter==0){
     * ingredientsList.addRow(ingredientsList.getRowCount(), c, t, deleteButton);
     * counter = 1;
     * } else {
     * ingredientsList2.addRow(ingredientsList2.getRowCount(),c,t,deleteButton);
     * counter = 0;
     * }
     */

    // Clear out input fields
    ingredientAmntField.clear();
    ingredientNameField.clear();
  }

  private String getFileName() {
    // TODO: Give nameField a better name
    return nameField.getText();
  }
}