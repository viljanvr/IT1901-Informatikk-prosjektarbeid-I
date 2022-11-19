package recipecalc.fxui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import recipecalc.client.RemoteRecipeCalcAccess;
import recipecalc.core.Ingredient;
import recipecalc.core.Recipe;
import recipecalc.data.RecipeFileHandler;

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
  private BorderPane header;

  @FXML
  private MFXTextField newIngredientNameField;

  @FXML
  private MFXTextField newIngredientAmntField;

  @FXML
  private MFXTextField newMeasurementField;

  @FXML
  private MFXTextField numberOfPeopleField;

  @FXML
  private MFXTextField recipeNameEditingField;

  @FXML
  private MFXButton addIngredient;

  @FXML
  private MFXButton decreasePeopleButton;

  @FXML
  private MFXButton increasePeopleButton;

  @FXML
  private MFXButton editButton;

  @FXML
  private MFXButton backButton;

  @FXML
  private MFXButton deleteRecipeButton;

  @FXML
  private VBox container;

  @FXML
  private VBox scaleBox;

  @FXML
  private VBox addIngredientBox;

  @FXML
  private Text numberOfPeopleErrorText;

  @FXML
  private Text addIngredientErrorText;

  @FXML
  private Text recipieNameText;

  @FXML
  private Text recipeNameErrorText;

  private Recipe recipe;

  private Boolean editMode = false;

  // Keeps track of left or right.
  private int columnCounter = 0;

  private Pane ingredientEditOverlay;

  private IngredientEditController ingredientEditController;

  private BoxBlur blur = new BoxBlur(30, 30, 3);

  private RemoteRecipeCalcAccess rrca = new RemoteRecipeCalcAccess("http://localhost", 8080);

  /**
   * Initializes the application.
   */
  public void initialize() {
    // Creates a copy, so that internal recipe object isn't mutable from the outside
    this.recipe = new Recipe(RecipeBookController.transferRecipe.getName(),
        (Ingredient[]) RecipeBookController.transferRecipe.getList()
            .toArray(new Ingredient[RecipeBookController.transferRecipe.getList().size()]));
    this.recipe.setNumberOfPeople(RecipeBookController.transferRecipe.getNumberOfPeople());

    initIngredientEditOverlay();
    loadRecipeFromRecipeBook(recipe);
    numberOfPeopleField.setText(String.valueOf(recipe.getNumberOfPeople()));

    initEventHandlers();
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
    editButton.setText(editMode ? "Done" : "Edit");
    addIngredientBox.setVisible(editMode);
    scaleBox.setVisible(!editMode);
    deleteRecipeButton.setVisible(editMode);
    header.setCenter(editMode ? recipeNameEditingField : recipieNameText);

    // Save new recipe name if texifield is changed and you exit editing mode
    if (!editMode && !Objects.equals(recipe.getName(), recipeNameEditingField.getText())) {
      saveNewRecipeName(recipeNameEditingField.getText());
    }
  }

  @FXML
  private void handleDecreasePeople() {
    if (recipe.getNumberOfPeople() == 2) {
      decreasePeopleButton.setDisable(true);
    }
    recipe.setNumberOfPeople(recipe.getNumberOfPeople() - 1);
    numberOfPeopleField.setText(String.valueOf(recipe.getNumberOfPeople()));
    updateIngredientListView();
    numberOfPeopleErrorText.setVisible(false);
  }

  @FXML
  private void handleIncreasePeople() {
    decreasePeopleButton.setDisable(false);
    recipe.setNumberOfPeople(recipe.getNumberOfPeople() + 1);
    numberOfPeopleField.setText(String.valueOf(recipe.getNumberOfPeople()));
    updateIngredientListView();
    numberOfPeopleErrorText.setVisible(false);
  }

  private void handleNumberOfPeopleChange() {
    try {
      int number = Integer.parseInt(numberOfPeopleField.getText());
      if (number > 0) {
        recipe.setNumberOfPeople(Integer.parseInt(numberOfPeopleField.getText()));
        numberOfPeopleErrorText.setVisible(false);
        if (number > 1) {
          decreasePeopleButton.setDisable(false);
        }
      } else {
        numberOfPeopleErrorText.setVisible(true);
      }

      updateIngredientListView();
    } catch (NumberFormatException e) {
      if (numberOfPeopleField.getText().isBlank()) {
        numberOfPeopleErrorText.setVisible(false);
      } else {
        numberOfPeopleErrorText.setVisible(true);
      }
    }
  }

  private void handleRecipeNameChange() {
    recipeNameErrorText
        .setVisible(!RecipeFileHandler.validFileName(recipeNameEditingField.getText()));
    editButton.setDisable(!RecipeFileHandler.validFileName(recipeNameEditingField.getText()));
  }

  private void saveNewRecipeName(String newName) {
    if (rrca.changeRecipeName(recipe.getName(), newName)) {
      recipe.setName(newName);
      recipieNameText.setText(newName);
    }
  }

  /**
   * Update the bought-value of a given ingredient in the recipe object when a checkBox is clicked.
   * The updated recipe is then saved to file.
   *
   * @param ingredientName String with the name of the ingredient
   * @param c Checkbox that has been clicked
   */
  private void handleToggleCheckbox(String ingredientName, MFXCheckbox c) {
    if (rrca.setBought(recipe.getName(), ingredientName, c.isSelected())) {
      recipe.setBought(ingredientName, c.isSelected());
    } else {
      c.setSelected(!c.isSelected());
    }
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
    if (rrca.deleteIngredient(recipe.getName(), ingredient)) {
      recipe.deleteItem(ingredient); // delete from database
      updateIngredientListView();
    }
  }

  /**
   * Clears the view and adds all items in recipe again.
   */
  private void updateIngredientListView() {
    clearIngredientListView();

    recipe.getList().stream().forEach(i -> {
      addItemToView(i.getName(), i.getTotalAmount(recipe.getNumberOfPeople()), i.getMeasuringUnit(),
          i.getBought());
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
      Double perPersonAmount, Double roundUpTo, String measuringUnit) {

    HashMap<String, Boolean> saveResults = rrca.updateIngredient(recipe.getName(), ingredient,
        newIngredientName, perPersonAmount, roundUpTo, measuringUnit);

    if (saveResults.get("setPerPersonAmount")) {
      recipe.setIngredientPerPersonAmount(ingredient, perPersonAmount);
    }
    if (saveResults.get("setRoundUpTo")) {
      recipe.setRoundUpTo(ingredient, roundUpTo);
    }
    if (saveResults.get("setMeasuringUnit")) {
      recipe.setIngredientMeasurement(ingredient, measuringUnit);
    }
    if (saveResults.get("changeIngredientName")) {
      recipe.changeIngredientName(ingredient, newIngredientName);
    }

    Text text = (Text) getIngredientViewStream()
        .filter(i -> i instanceof Text && ((Text) i).getText().contains(ingredient)).findFirst()
        .get();

    text.setText(Ingredient.formatDouble(recipe.getIngredientTotalAmount(newIngredientName)) + " "
        + measuringUnit + " " + newIngredientName);
  }

  /**
   * Method takes in the name of an ingredient and checks if there is an item i view with the same
   * name.
   *
   * @param name String of the name of ingredient to be added
   * @return true if ingredient is already in view
   */
  public boolean isDuplicate(String name) {
    Ingredient duplicate = recipe.getIngredient(name);
    return duplicate != null;
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
      String ingredientUnit = newMeasurementField.getText();

      if (!isDuplicate(ingredientName)) {
        if (rrca.addIngredient(recipe.getName(),
            new Ingredient(ingredientName, ingredientPerPersonAmnt, ingredientUnit))) {
          recipe.addItem(ingredientName, ingredientPerPersonAmnt, ingredientUnit);

          addItemToView(ingredientName, recipe.getIngredientTotalAmount(ingredientName),
              ingredientUnit, false);
        }
      }
      newIngredientAmntField.clear();
      newIngredientNameField.clear();
      newMeasurementField.clear();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }

  private void validateAddNewIngredientFields() {
    Boolean nameEmpty = newIngredientNameField.getText().isEmpty();
    Boolean nameError =
        !(Ingredient.isValidIngredientName(newIngredientNameField.getText()) || nameEmpty);
    Boolean unitEmpty = newMeasurementField.getText().isEmpty();
    Boolean unitError =
        !(Ingredient.isValidMeasuringUnit(newMeasurementField.getText()) || unitEmpty);
    Boolean amountEmpty = false;
    Boolean amountError = false;


    if (newIngredientAmntField.getText().isEmpty()) {
      amountEmpty = true;
    } else {
      try {
        Double amount = Double.parseDouble(newIngredientAmntField.getText());
        amountError = !Ingredient.isValidPerPersonAmount(amount);
      } catch (NumberFormatException e) {
        amountError = true;
      }
    }

    if (nameError || unitError || amountError) {
      addIngredient.setDisable(true);
      addIngredientErrorText.setVisible(true);

      Boolean severalErrors =
          new ArrayList<Boolean>(Arrays.asList(nameError, unitError, amountError)).stream()
              .filter(e -> e).count() > 1;

      addIngredientErrorText.setText(severalErrors ? "There are several invalid fields"
          : nameError ? "The name is invalid"
              : unitError ? "The measuring unit is invalid" : "The amount value is unvalid");
    } else {
      addIngredient.setDisable(nameEmpty || unitEmpty || amountEmpty);
      addIngredientErrorText.setVisible(false);
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
  private void addItemToView(String ingredientName, Double ingredientAmnt, String measuringUnit,
      Boolean checked) {
    MFXCheckbox checkBox = new MFXCheckbox();
    checkBox.setSelected(checked);

    MFXButton editButton = new MFXButton("→");
    editButton.setVisible(editMode);
    editButton.setStyle("-fx-font-size: 20;");

    Text text = new Text(
        Ingredient.formatDouble(ingredientAmnt) + " " + measuringUnit + " " + ingredientName);
    text.setFill(Color.WHITE);
    text.setStyle("-fx-font-size: 20;");

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
        handleToggleCheckbox(ingredientName, (MFXCheckbox) e.getSource());
      }
    });

    if (columnCounter == 0) {
      ingredientsListLeft.addRow(ingredientsListLeft.getRowCount(), checkBox, text, editButton);
      columnCounter = 1;
    } else {
      ingredientsListRight.addRow(ingredientsListRight.getRowCount(), checkBox, text, editButton);
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

  @FXML
  private void clickOutsideOverlayHandle(MouseEvent e) {
    if (Objects.equals(e.getPickResult().getIntersectedNode().getId(), "popUpContain")) {
      closeIngredientEditOverlay();
    }
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
   * A getter that maskes the newIngredientAmntField visible to other classes is used in tests.
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
   * Initialises the view with all the ingredients from the recipie, and sets the recipe title.
   *
   * @param recipe name of the recipie to load
   */
  public void loadRecipeFromRecipeBook(Recipe recipe) {
    updateIngredientListView();
    recipieNameText.setText(recipe.getName());
    recipeNameEditingField = new MFXTextField(recipe.getName(), "", "Recipe Name");
    recipeNameEditingField.setFloatMode(FloatMode.BORDER);
    recipeNameEditingField.setId("recipe-name-text-field");
    recipeNameEditingField.setPrefWidth(200);
  }

  @FXML
  private void handleGoBack() {
    Stage thisStage = (Stage) backButton.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeBook.fxml"));
    Parent root;
    try {
      root = loader.load();
      thisStage.setScene(new Scene(root));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleDeleteRecipe() {
    RecipeFileHandler.deleteFile(recipe.getName());
    handleGoBack();
  }

  private void initEventHandlers() {
    numberOfPeopleField.addEventFilter(KeyEvent.ANY, e -> {
      handleNumberOfPeopleChange();
    });

    recipeNameEditingField.addEventFilter(KeyEvent.ANY, e -> {
      handleRecipeNameChange();
    });

    newIngredientNameField.addEventFilter(KeyEvent.ANY, e -> {
      validateAddNewIngredientFields();
    });

    newIngredientAmntField.addEventFilter(KeyEvent.ANY, e -> {
      validateAddNewIngredientFields();
    });

    newMeasurementField.addEventFilter(KeyEvent.ANY, e -> {
      validateAddNewIngredientFields();
    });
  }

  protected IngredientEditController getIngredientEditController() {
    return this.ingredientEditController;
  }
}
