package tacocalc.fxui;

import java.io.IOException;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import tacocalc.core.ShoppingList;
import tacocalc.persistence.TacoCalcFileHandler;

public class AppController {

  // Connects the main Shoppingslist class to the FXML file
  @FXML
  private GridPane ingredientsListLeft, ingredientsListRight;

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

  // Keeps track of left or right.
  private int counter = 0;

  private Pane ingredientEditOverlay;

  private IngredientEditController ingredientEditController;

  public void initialize() {
    ingredientEditController = new IngredientEditController(this);
    ingredientEditOverlay = loadIngredientEditOverlay();
    popUpContain.setCenter(ingredientEditOverlay);
    popUpContain.setVisible(false);
  }


  @FXML
  private void handleEditButton() {
    editMode = !editMode;
    ingredientsListLeft.getChildren().stream().filter(a -> a instanceof Button)
        .forEach(a -> a.setVisible(editMode));
    ingredientsListRight.getChildren().stream().filter(a -> a instanceof Button)
        .forEach(a -> a.setVisible(editMode));
    editButton.setText(editMode ? "Cancel" : "Edit");
  }

  /*
   * Finds and deletes the given ingredient from the current locally stored database as well as the
   * Gridpane
   * 
   * @params: ingredient, c, d the String name of the ingredient, and the Checkbox and Button of the
   * ingredient
   * 
   */
  // private void handleDelete(String ingredient, CheckBox c, MenuButton d) {

  // shoppingList.deleteItem(ingredient); // delete from database
  // List<Node> children =
  // ingredientsListLeft.getChildren().stream().filter(n -> (n.equals(c) || n.equals(d)
  // // TODO: Change "contains" so you can't remove duplicates at the same time
  // || (n instanceof TextField && ((TextField) n).getText().contains(ingredient))))
  // .collect(Collectors.toList());
  // for (Node n : children) {
  // ingredientsListLeft.getChildren().remove(n);
  // }
  // children = ingredientsListRight.getChildren().stream().filter(n -> (n.equals(c) || n.equals(d)
  // // TODO: Change "contains" so you can't remove duplicates at the same time
  // || (n instanceof TextField && ((TextField) n).getText().contains(ingredient))))
  // .collect(Collectors.toList());
  // for (Node n : children) {
  // ingredientsListRight.getChildren().remove(n);
  // }
  // handleSaveToFile();
  // }

  /**
   * Toggles checkbox between checked or unchecked
   * 
   * @param ingredientName String with the name of the ingredient
   * @param c Checkbox to be checked or unchecked
   */
  private void handleToggleCheckbox(String ingredientName, CheckBox c) {
    shoppingList.setBought(ingredientName, c.isSelected());
    handleSaveToFile();
  }

  protected void handleSaveToFile() {
    TacoCalcFileHandler fh = new TacoCalcFileHandler();
    fh.write(shoppingList, getFileName());
  }

  /*
   * Removes all items from local gridpane. Reads file before adding all elements found in file to
   * the local shoppinglist. Iterates over ShoppingList and adds all to view.
   */
  @FXML
  private void handleLoadFile() {
    this.ingredientsListLeft.getChildren().clear();
    this.ingredientsListRight.getChildren().clear();
    counter = 0;
    TacoCalcFileHandler fh = new TacoCalcFileHandler();
    this.shoppingList = fh.read(shoppingList, getFileName());
    shoppingList.getList().stream()
        .forEach(n -> addItemToView(n.getName(), n.getAmount(), n.getBought()));
  }

  /*
   * Adds ingredient to ShoppingList object. Saves the current ShoppingList object to a JSON-file.
   * Updates the shopping list view with the new ingredient.
   * 
   * In case an illegal amount is specified, an alert is showed.
   */

  @FXML
  private void handleAddIngredient() {
    try {
      String ingredientName = ingredientNameField.getText();
      Integer ingredientAmnt = Integer.parseInt(ingredientAmntField.getText());

      shoppingList.addItem(ingredientName, ingredientAmnt);
      handleSaveToFile();

      addItemToView(ingredientName, ingredientAmnt, false);

    } catch (Exception e) {
      Alert a = new Alert(AlertType.ERROR);
      a.setContentText("Amount needs to be a valid integer");
      a.show();
      e.printStackTrace();
    }
  }

  protected void updateIngredient(String ingredient, String newIngredientName, int amount) {
    shoppingList.setIngredientAmount(ingredient, amount);
    shoppingList.changeIngredientName(ingredient, newIngredientName);

    TextField textField = ((TextField) getIngredientStream()
        .filter(i -> i instanceof TextField && ((TextField) i).getText().contains(ingredient))
        .findFirst().get());

    textField.setText(amount + "x " + newIngredientName);

    handleSaveToFile();
  }

  private Stream<Node> getIngredientStream() {
    return Stream.concat(ingredientsListLeft.getChildren().stream(),
        ingredientsListRight.getChildren().stream());
  }

  @FXML
  public void handleGoBack() {
    popUpContain.setVisible(false);
  }

  private void buildEditPane(String ingredientName) {
    popUpContain.setVisible(true);
    ingredientEditController.showNewIngredient(ingredientName, shoppingList);


    // TODO: The Controller needs to make a correct controller for the given
    // ingredientname
  }

  private Pane loadIngredientEditOverlay() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PopupMenu.fxml"));
    fxmlLoader.setController(ingredientEditController);
    try {
      return fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Method takes in an Ingredient and adds it to the view Adds the Ingredient with its children to
   * the view, and deletes the content of the textfields
   * 
   * Method also contains the eventhandlers for ToggleCheckbox, and the Delete function
   * 
   * 
   * @param ingredientName the string of the name
   * @param ingredientAmnt the integer of the amount
   * @param checked the boolean state of the checkbox
   */
  private void addItemToView(String ingredientName, Integer ingredientAmnt, Boolean checked) {
    CheckBox c = new CheckBox();
    c.setSelected(checked);

    Button editButton = new Button("->");
    editButton.setVisible(editMode);

    TextField t = new TextField(ingredientAmnt + "x " + ingredientName);
    t.setEditable(false);

    // TODO: Make this an edit button instead
    editButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        buildEditPane(ingredientName);
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
      ingredientsListLeft.addRow(ingredientsListLeft.getRowCount(), c, t, editButton);
      counter = 1;
    } else {
      ingredientsListRight.addRow(ingredientsListRight.getRowCount(), c, t, editButton);
      counter = 0;
    }

    ingredientAmntField.clear();
    ingredientNameField.clear();
  }

  private String getFileName() {
    // TODO: Give nameField a better name
    return nameField.getText();
  }

  public TextField getIngredientAmntField() {
    return new TextField(this.ingredientAmntField.getText());
  }

  public TextInputControl getIngredientNameField() {
    return new TextField(this.ingredientNameField.getText());
  }
}
