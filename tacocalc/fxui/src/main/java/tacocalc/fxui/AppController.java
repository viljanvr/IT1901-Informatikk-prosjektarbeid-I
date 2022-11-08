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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tacocalc.core.Ingredient;
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
  private TextField nameField;

  @FXML
  private TextField numberOfPeopleField;

  @FXML
  private Button addIngredient;

  @FXML
  private Button decreasePeopleButton;

  @FXML
  private Button increasePeopleButton;

  @FXML
  private Button editButton;

  @FXML
  private Button loadButton;

  @FXML
  private VBox container;

  @FXML
  private HBox scaleBox;

  @FXML
  private HBox addIngredientBox;

  private Boolean editMode = false;

  private Recipe recipe = new Recipe();

  // Keeps track of left or right.
  private int columnCounter = 0;

  private Pane ingredientEditOverlay;

  private IngredientEditController ingredientEditController;

  private BoxBlur blur = new BoxBlur(30, 30, 3);

  /**
   * Initializes the application.
   */
  public void initialize() {
    initIngredientEditOverlay();
    loadRecipeFromRecipeBook(RecipeBookController.transferRecipe);
    numberOfPeopleField.setText(String.valueOf(recipe.getNumberOfPeople()));
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
    addIngredientBox.setVisible(editMode);
    scaleBox.setVisible(!editMode);
  }

  @FXML
  private void handleDecreasePeople() {
    numberOfPeopleField
        .setText(String.valueOf(Integer.parseInt(numberOfPeopleField.getText()) - 1));
    recipe.setNumberOfPeople(recipe.getNumberOfPeople() - 1);
    updateIngredientListView();
  }

  @FXML
  private void handleIncreasePeople() {
    numberOfPeopleField
        .setText(String.valueOf(Integer.parseInt(numberOfPeopleField.getText()) + 1));
    recipe.setNumberOfPeople(recipe.getNumberOfPeople() + 1);
    updateIngredientListView();
  }

  @FXML
  private void handleNumberOfPeopleChange() {
    try {
      recipe.setNumberOfPeople(Integer.parseInt(numberOfPeopleField.getText()));
      updateIngredientListView();
    } catch (NumberFormatException e) {
      System.out.println("Not a valid number");
    }
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
      addItemToView(i.getName(), i.getTotalAmount(recipe.getNumberOfPeople()), i.getBought());
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
  protected void updateIngredient(String ingredient, String newIngredientName,
      Double perPersonAmount) {
    recipe.setIngredientPerPersonAmount(ingredient, perPersonAmount);
    recipe.changeIngredientName(ingredient, newIngredientName);

    TextField textField = (TextField) getIngredientViewStream()
        .filter(i -> i instanceof TextField && ((TextField) i).getText().contains(ingredient))
        .findFirst().get();

    textField.setText(Ingredient.formatDouble(perPersonAmount * recipe.getNumberOfPeople()) + "x "
        + newIngredientName);

    handleSaveToFile();
  }

  /**
   * Methods takes in the name of the ingredient we want to add to our recipe and checks if that
   * ingredient is already in recipe.
   *
   * @param name the String of the ingredients name
   * @return true if an ingredient with that name exists in view
   */
  private boolean isDuplicate(String name) {
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
      String ingredientName = newIngredientNameField.getText().toLowerCase();
      Double ingredientPerPersonAmnt = Double.parseDouble(newIngredientAmntField.getText());

      recipe.addItem(ingredientName, ingredientPerPersonAmnt);
      handleSaveToFile();

      if (isDuplicate(ingredientName)) {
        updateIngredient(ingredientName, ingredientName,
            ingredientPerPersonAmnt * recipe.getNumberOfPeople());
        updateIngredientListView();
      } else {
        addItemToView(ingredientName, ingredientPerPersonAmnt * recipe.getNumberOfPeople(), false);
      }

      newIngredientAmntField.clear();
      newIngredientNameField.clear();
    } catch (NumberFormatException e) {
      Alert a = new Alert(AlertType.ERROR);
      a.setContentText("Amount needs to be a valid integer");
      a.show();
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
   */
  private void addItemToView(String ingredientName, Double ingredientAmnt, Boolean checked) {
    CheckBox checkBox = new CheckBox();
    checkBox.setSelected(checked);

    Button editButton = new Button("->");
    editButton.setVisible(editMode);

    TextField textField =
        new TextField(Ingredient.formatDouble(ingredientAmnt) + "x " + ingredientName);
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
    } else {
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
    handleEditButton();
    popUpContain.setVisible(true);
    ingredientEditController.showNewIngredient(ingredientName, recipe);
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
    updateIngredientListView();
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
