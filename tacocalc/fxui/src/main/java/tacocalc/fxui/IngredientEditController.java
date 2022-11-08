package tacocalc.fxui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tacocalc.core.Ingredient;
import tacocalc.core.Recipe;

/**
 * Controller for the overlay where you can edit the properties of a single ingredient.
 */
public class IngredientEditController {

  @FXML
  private TextField ingredientNameField;

  @FXML
  private TextField measuringUnitField;

  @FXML
  private TextField quantityField;

  @FXML
  private TextField roundAmountField;

  @FXML
  private Button decreaseButton;

  @FXML
  private Button increaseButton;

  @FXML
  private Button saveButton;

  @FXML
  private Button deleteButton;

  @FXML
  private CheckBox roundCheckBox;

  @FXML
  private Text roundUpToText;

  String ingredientName;
  Recipe recipe;
  AppController appController;

  protected IngredientEditController(AppController appController) {
    this.appController = appController;
  }

  /**
   * Updates the edit screen to show the name, amount and measuring unit of a single ingredient.
   *
   * @param ingredientName name of the given ingredient
   * @param recipe internal object containing all ingredient objects
   */
  protected void showNewIngredient(String ingredientName, Recipe recipe) {
    this.ingredientName = ingredientName;
    this.recipe = recipe;

    ingredientNameField.setText(ingredientName);
    quantityField.setText(Double.toString(recipe.getIngredientPerPersonAmount(ingredientName)));

    // TODO: add logic to set measuring unit
    measuringUnitField.setText("stk");

    if (recipe.getRoundUpTo(ingredientName) == 0.0) {
      handleRoundToggle();
    } else {
      roundAmountField.setText(Ingredient.formatDouble(recipe.getRoundUpTo(ingredientName)));
      roundCheckBox.setSelected(true);
    }
  }

  /**
   * Decreases the amount in the quantity field when pressing the minus-button.
   */
  @FXML
  private void handleDecrease() {
    Double amount = recipe.getIngredientPerPersonAmount(ingredientName);
    recipe.setIngredientPerPersonAmount(ingredientName, amount - 1);
    quantityField.setText(Double.toString(amount - 1));
  }

  /**
   * Increases the amount in the quantity field when pressing the plus-button.
   */
  @FXML
  private void handleIncrease() {
    Double amount = recipe.getIngredientPerPersonAmount(ingredientName);
    recipe.setIngredientPerPersonAmount(ingredientName, amount + 1);
    quantityField.setText(Double.toString(amount + 1));
  }

  @FXML
  private void handleRoundToggle() {
    roundUpToText.setFill(roundCheckBox.isSelected() ? Color.WHITE : Color.web("#ababab"));
    roundAmountField.setDisable(!roundCheckBox.isSelected());
    roundAmountField.setText(
        roundCheckBox.isSelected() ? Ingredient.formatDouble(recipe.getRoundUpTo(ingredientName))
            : "");
  }

  /**
   * Saves the changes to a given ingredient when pressing the save-button and closes the editing.
   * overlay.
   */
  @FXML
  protected void handleSave() {
    appController.closeIngredientEditOverlay();
    appController.updateIngredient(ingredientName, ingredientNameField.getText().toLowerCase(),
        Double.parseDouble(quantityField.getText()));
  }

  /**
   * Deletes the ingredient when pressing the delete-button and closes the editing overlay.
   */
  @FXML
  private void handleDelete() {
    appController.closeIngredientEditOverlay();
    appController.handleDeleteIngredient(ingredientName);
  }

}
