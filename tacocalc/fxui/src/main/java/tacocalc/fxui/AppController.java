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
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tacocalc.core.Recipe;
import tacocalc.data.TacoCalcFileHandler;

/**
 * Main controller for screen where the user can see their shopping list.
 */
public class AppController {
  // Connects the main Shoppingslist class to the FXML file
  @FXML
  private GridPane ingredientsListLeft;

  @FXML
  private GridPane ingredientsListRight;

  @FXML
  private BorderPane popUpContain;

  @FXML
  private TextField newIngredientNameField;

  @FXML
  private TextField newIngredientAmntField;

  @FXML
  private TextField newMeasurementField;

  @FXML
  private TextField nameField;

  @FXML
  private Button addIngredient;

  @FXML
  private Button goBackButton;

  @FXML
  private Button editButton;

  @FXML
  private Button loadButton;

  @FXML
  private VBox container;

  private Boolean editMode = false;

  private Recipe recipe = new Recipe();

  // Keeps track of left or right.
  private int columnCounter = 0;

  private Pane ingredientEditOverlay;

  private IngredientEditController ingredientEditController;

  private BoxBlur blur = new BoxBlur(30, 30, 3);

  public void initialize() {
    initIngredientEditOverlay();
    loadRecipeFromRecipeBook(RecipeBookController.transferRecipe);
  }

  /**
   * Enables/disables edit view when pressing the edit button. When entering edit mode a button to
   * the right of each ingredient is shown. The button opens an overlay where you can edit the
   * properties of the given ingredient.
   */
  @FXML
  private void handleEditButton() {
    editMode = !editMode;
    getIngredientViewStream().filter(a -> a instanceof Button).forEach(a -> a.setVisible(editMode));
    editButton.setText(editMode ? "Cancel" : "Edit");
  }

  /**
   * Update the bought-value of a given ingredient in the recipe object when a checkBox is clicked.
   * The updated recipe is then saved to file.
   *
   * @param ingredientName String with the name of the ingredient
   * @param c Checkbox that has been clicked
   */
  private void handleToggleCheckbox(String ingredientName, CheckBox c) {
    recipe.setBought(ingredientName, c.isSelected());
    handleSaveToFile();
  }

  /**
   * Finds and deletes the given ingredient in the recipe object. Saves updated recipe to files and
   * updates the view.
   *
   * @param ingredient name of the ingredient to be removed
   *
   *
   */
  protected void handleDeleteIngredient(String ingredient) {
    recipe.deleteItem(ingredient); // delete from database
    updateIngredientListView();
    handleSaveToFile();
  }

  /**
   * Clears the view and adds all items in recipe again.
   */
  private void updateIngredientListView() {
    clearIngredientListView();

    recipe.getList().stream().forEach(i -> {
      addItemToView(i.getName(), i.getAmount(), i.getBought(), i.getMeasuringUnit());
    });
  }

  /**
   * Clears the view of all ingredients.
   */
  private void clearIngredientListView() {
    ingredientsListLeft.getChildren().clear();
    ingredientsListRight.getChildren().clear();

    columnCounter = 0;
  }

  /**
   * Updates the internal value of a single ingredient in the recipe object. Updated recipe is then
   * saved to file and the view is updated.
   *
   * @param ingredient ingredient to be changed
   * @param newIngredientName new ingredient name
   * @param amount new amount to be set
   */
  protected void updateIngredient(String ingredient, String newIngredientName, int amount,
      String measuringUnit) {
    recipe.setIngredientAmount(ingredient, amount);
    recipe.setIngredientMeasurement(ingredient, measuringUnit);
    recipe.changeIngredientName(ingredient, newIngredientName);

    // TODO: Update the textfields for measuringUnit

    TextField textField = (TextField) getIngredientViewStream()
        .filter(i -> i instanceof TextField && ((TextField) i).getText().contains(ingredient))
        .findFirst().get();

    textField.setText(amount + "x " + newIngredientName + " " + measuringUnit);

    handleSaveToFile();
  }

  /**
   * Method takes in the name of an ingredient and checks if there is an item i view with the same
   * name
   * 
   * @param ingredientName String of the name of ingredient to be added
   * @return true if ingredient is already in view
   */
  public boolean isDuplicate(String name) {
    try {
      getIngredientViewStream()
          .filter(i -> i instanceof TextField && ((TextField) i).getText().contains(name))
          .findFirst().get();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Adds ingredient to the ShoppingList object. Saves the updated recipe object to file and updates
   * the view.
   *
   * <p>
   * In case an illegal amount is specified, an alert is showed.
   */
  @FXML
  private void handleAddIngredient() {
    try {
      String ingredientName = newIngredientNameField.getText();
      Integer ingredientAmnt = Integer.parseInt(newIngredientAmntField.getText());
      String ingredientUnit = newMeasurementField.getText();

      recipe.addItem(ingredientName, ingredientAmnt, ingredientUnit);
      handleSaveToFile();

      if (isDuplicate(ingredientName)) {
        updateIngredient(ingredientName, ingredientName, ingredientAmnt, ingredientUnit);
        updateIngredientListView();
      }
      else {
        addItemToView(ingredientName, ingredientAmnt, false, ingredientUnit);
      }

      newIngredientAmntField.clear();
      newIngredientNameField.clear();
      newMeasurementField.clear();
    } catch (Exception e) {
      Alert a = new Alert(AlertType.ERROR);
      a.setContentText("Amount needs to be a valid integer");
      a.show();
      e.printStackTrace();
    }
  }

  /**
   * Method takes in the properties of an ingredient and adds it to the view.
   * 
   * <p>
   * Method also initialises the eventhandlers for the new checkbox and the edit-button for the new
   * ingredient.
   *
   * @param ingredientName the string of the name
   * @param ingredientAmnt the integer of the amount
   * @param checked the boolean state of the checkbox
   * @param measuringUnit the string of the measuring unit
   */
  private void addItemToView(String ingredientName, Integer ingredientAmnt, Boolean checked,
      String measuringUnit) {
    CheckBox checkBox = new CheckBox();
    checkBox.setSelected(checked);

    Button editButton = new Button("->");
    editButton.setVisible(editMode);

    TextField textField =
        new TextField(ingredientAmnt + "x " + ingredientName + " " + measuringUnit);
    textField.setEditable(false);
    // Event handler for ingredient edit button
    editButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent e) {
        openIngredientEditOverlay(ingredientName);
      }
    });

    // Event handler for checkbox
    checkBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent e) {
        handleToggleCheckbox(ingredientName, (CheckBox) e.getSource());
      }
    });

    if (columnCounter == 0) {
      ingredientsListLeft.addRow(ingredientsListLeft.getRowCount(), checkBox, textField,
          editButton);
      columnCounter = 1;
    }
    else {
      ingredientsListRight.addRow(ingredientsListRight.getRowCount(), checkBox, textField,
          editButton);
      columnCounter = 0;
    }
  }

  /**
   * Combines a stream of Nodes from the left and right ingredient column.
   *
   * @return returns a stream with Nodes.
   */
  protected Stream<Node> getIngredientViewStream() {
    return Stream.concat(ingredientsListLeft.getChildren().stream(),
        ingredientsListRight.getChildren().stream());
  }

  /**
   * Closes the overlay where you can edit the properties of an ingredient.
   */
  protected void closeIngredientEditOverlay() {
    popUpContain.setVisible(false);
    container.setEffect(null);
  }

  /**
   * Opens the overlay where you can edit the properties of a given ingredient. Updates the overlay
   * with the values of the given ingredient.
   *
   * @param ingredientName the ingredient to be edited
   */
  private void openIngredientEditOverlay(String ingredientName) {
    popUpContain.setVisible(true);
    ingredientEditController.showNewIngredient(ingredientName, recipe,
        recipe.getIngredient(ingredientName).getMeasuringUnit());
    container.setEffect(blur);
  }

  /**
   * Initalises the overlay for editing the properties of single ingredients.
   */
  private void initIngredientEditOverlay() {
    ingredientEditController = new IngredientEditController(this);

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PopupMenu.fxml"));
    fxmlLoader.setController(ingredientEditController);
    try {
      ingredientEditOverlay = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    popUpContain.setCenter(ingredientEditOverlay);
  }

  /**
   * Saves the recipe object to a file with the name from the nameField text field.
   */
  protected void handleSaveToFile() {
    TacoCalcFileHandler fh = new TacoCalcFileHandler();
    fh.write(recipe, getFileName());
  }

  /**
   * Clears the ingredient view. Reads a file with ingredients and adds all elements to the a recipe
   * object. All elements of recipe is then added to the view.
   */
  @FXML
  private void handleLoadFile() {
    clearIngredientListView();
    TacoCalcFileHandler fh = new TacoCalcFileHandler();
    this.recipe = fh.read(getFileName());
    recipe.getList().stream().forEach(
        n -> addItemToView(n.getName(), n.getAmount(), n.getBought(), n.getMeasuringUnit()));
  }

  /**
   * Gets the name from a text field of a file to be read or written to.
   *
   * <p>
   * TODO: Implement possibility to have different recipes and files
   *
   * @return returns filename as a String
   */
  private String getFileName() {
    return nameField.getText();
  }

  /**
   * A getter that maskes the newIngredientAmntField visible to other classes Is used in tests.
   *
   * @return returns the TextField object
   */
  public TextField getNewIngredientAmntField() {
    return new TextField(this.newIngredientAmntField.getText());
  }

  /**
   * A getter that makes the newingredientNameField visible to other classes Is used in test.
   *
   * @return returns the TextField object
   */
  public TextInputControl getNewIngredientNameField() {
    return new TextField(this.newIngredientNameField.getText());
  }

  /**
   * TODO: write JavaDoc.
   *
   * @param recipeName name of the recipie to load
   */
  public void loadRecipeFromRecipeBook(String recipeName) {
    TacoCalcFileHandler fh = new TacoCalcFileHandler();
    this.recipe = fh.read(recipeName);
    updateIngredientListView();
  }
}
