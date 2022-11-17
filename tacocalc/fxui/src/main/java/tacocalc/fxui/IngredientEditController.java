package tacocalc.fxui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tacocalc.core.Ingredient;
import tacocalc.core.Recipe;

/**
 * Controller for the overlay where you can edit the properties of a single ingredient.
 */
public class IngredientEditController {

  @FXML
  private MFXTextField ingredientNameField;

  @FXML
  private MFXTextField measuringUnitField;

  @FXML
  private MFXTextField quantityField;

  @FXML
  private MFXTextField roundAmountField;

  @FXML
  private MFXButton decreaseButton;

  @FXML
  private MFXButton increaseButton;

  @FXML
  private MFXButton saveButton;

  @FXML
  private MFXButton deleteIngredientButton;

  @FXML
  private MFXCheckbox roundCheckBox;

  @FXML
  private Text roundUpToText;

  @FXML
  private Text errorMessage;

  String ingredientName;
  Recipe recipe;
  AppController appController;
  Double newPerPersonAmount;

  protected IngredientEditController(AppController appController) {
    this.appController = appController;
  }

  public void initialize() {
    initEventHandlers();
  }

  /**
   * Updates the edit screen to show the name, amount and measuring unit of a single ingredient.
   *
   * @param ingredientName name of the given ingredient
   * @param recipe internal object containing all ingredient objects
   */
  protected void showNewIngredient(String ingredientName, Recipe recipe, String unit) {
    this.ingredientName = ingredientName;
    this.recipe = recipe;
    this.newPerPersonAmount = recipe.getIngredientPerPersonAmount(ingredientName);

    ingredientNameField.setText(ingredientName);
    quantityField.setText(Double.toString(this.newPerPersonAmount));

    if (newPerPersonAmount > 1) {
      decreaseButton.setDisable(false);
    } else {
      decreaseButton.setDisable(true);
    }

    // TODO: add logic to set measuring unit
    measuringUnitField.setText(unit);

    if (recipe.getRoundUpTo(ingredientName) == 0.0) {
      handleRoundToggle();
    } else {
      roundAmountField.setText(String.valueOf(recipe.getRoundUpTo(ingredientName)));
      roundCheckBox.setSelected(true);
    }
  }

  /**
   * Decreases the amount in the quantity field when pressing the minus-button.
   */
  @FXML
  private void handleDecrease() {
    newPerPersonAmount -= 1;
    quantityField.setText(Double.toString(newPerPersonAmount));
    if (newPerPersonAmount == 1) {
      decreaseButton.setDisable(true);
    }
  }

  /**
   * Increases the amount in the quantity field when pressing the plus-button.
   */
  @FXML
  private void handleIncrease() {
    newPerPersonAmount += 1;
    quantityField.setText(Double.toString(newPerPersonAmount));
    decreaseButton.setDisable(false);
  }

  @FXML
  private void test123() {
    System.out.println(ingredientNameField.getPseudoClassStates().toString());
  }

  private void inputValidation() {
    Boolean amountError = false;
    Boolean roundError = false;

    // Check for error in the amount per person field
    try {
      Double amount = Double.parseDouble(quantityField.getText());
      if (amount <= 0) {
        amountError = true;
      } else if (amount <= 1) {
        decreaseButton.setDisable(true);
      } else {
        decreaseButton.setDisable(false);
      }
    } catch (NumberFormatException e) {
      amountError = true;
    }

    // Check for error in the round up to field
    try {
      Double value = Double.parseDouble(roundAmountField.getText());
      if (value <= 0 && roundCheckBox.isSelected()) {
        roundError = true;
      }
    } catch (NumberFormatException e) {
      roundError = true;
    }

    if (amountError && roundError) {
      saveButton.setDisable(true);
      errorMessage.setVisible(true);
      errorMessage.setText("Several values are incorrect");
    } else if (amountError) {
      saveButton.setDisable(true);
      errorMessage.setVisible(true);
      errorMessage.setText("The amount per person value is not valid");
    } else if (roundError) {
      saveButton.setDisable(true);
      errorMessage.setVisible(true);
      errorMessage.setText("The round up to value is not valid");
    } else {
      saveButton.setDisable(false);
      errorMessage.setVisible(false);
    }
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
    appController.updateIngredient(ingredientName, ingredientNameField.getText().toLowerCase(),
        Double.parseDouble(quantityField.getText()),
        roundCheckBox.isSelected() ? Double.parseDouble(roundAmountField.getText()) : 0,
        measuringUnitField.getText());
    appController.closeIngredientEditOverlay();
  }

  /**
   * Deletes the ingredient when pressing the delete-button and closes the editing overlay.
   */
  @FXML
  private void handleDelete() {
    appController.closeIngredientEditOverlay();
    appController.handleDeleteIngredient(ingredientName);
  }

  private void initEventHandlers() {
    quantityField.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        inputValidation();
      }
    });

    roundAmountField.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        inputValidation();
      }
    });
  }

  protected Button getSaveButton() {
    return this.saveButton;
  }
}
