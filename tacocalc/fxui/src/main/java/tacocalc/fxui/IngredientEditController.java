package tacocalc.fxui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ArrayList;
import java.util.Arrays;
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
  AppController appController;
  Double newPerPersonAmount;
  Double temporaryRoundUp;

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
    this.newPerPersonAmount = recipe.getIngredientPerPersonAmount(ingredientName);

    ingredientNameField.setText(ingredientName);
    quantityField.setText(Double.toString(this.newPerPersonAmount));

    if (newPerPersonAmount > 1) {
      decreaseButton.setDisable(false);
    } else {
      decreaseButton.setDisable(true);
    }

    measuringUnitField.setText(unit);

    temporaryRoundUp = recipe.getRoundUpTo(ingredientName);
    roundAmountField.clear();
    roundCheckBox.setSelected(temporaryRoundUp != 0.0);
    handleRoundToggle();
  }

  /**
   * Decreases the amount in the quantity field when pressing the minus-button.
   */
  @FXML
  private void handleDecrease() {
    newPerPersonAmount = Math.ceil(newPerPersonAmount) - 1;
    quantityField.setText(Double.toString(newPerPersonAmount));
    decreaseButton.setDisable(newPerPersonAmount <= 1);
    inputValidation();

  }

  /**
   * Increases the amount in the quantity field when pressing the plus-button.
   */
  @FXML
  private void handleIncrease() {
    newPerPersonAmount = Math.floor(newPerPersonAmount) + 1;
    quantityField.setText(Double.toString(newPerPersonAmount));
    decreaseButton.setDisable(newPerPersonAmount <= 1);
    inputValidation();
  }

  private void inputValidation() {
    Boolean nameError = !Ingredient.isValidIngredientName(ingredientNameField.getText());
    Boolean unitError = !Ingredient.isValidMeasuringUnit(measuringUnitField.getText());
    Boolean amountError = false;
    Boolean roundError = false;

    // Check for error in the amount per person field
    try {
      newPerPersonAmount = Double.parseDouble(quantityField.getText());
      if (!Ingredient.isValidPerPersonAmount(newPerPersonAmount)) {
        amountError = true;
      } else {
        decreaseButton.setDisable(newPerPersonAmount <= 1.0);
      }
    } catch (NumberFormatException e) {
      amountError = true;
    }

    // Check for error in the round up to field
    if (roundCheckBox.isSelected()) {
      try {
        Double value = Double.parseDouble(roundAmountField.getText());
        roundError = value <= 0;
      } catch (NumberFormatException e) {
        roundError = true;
      }
    }

    if (nameError || unitError || amountError || roundError) {
      saveButton.setDisable(true);
      errorMessage.setVisible(true);
      Boolean severalErrors =
          new ArrayList<Boolean>(Arrays.asList(nameError, unitError, amountError, roundError))
              .stream().filter(e -> e).count() > 1;

      errorMessage.setText(severalErrors ? "Several values are incorrect"
          : nameError ? "The ingredient name is not valid"
              : unitError ? "The measuring unit is not valid"
                  : amountError ? "The amount per person value is not valid"
                      : "The round up amount value is not valid");
    } else {
      saveButton.setDisable(false);
      errorMessage.setVisible(false);
    }
  }

  @FXML
  private void handleRoundToggle() {
    roundUpToText.setFill(roundCheckBox.isSelected() ? Color.WHITE : Color.web("#ababab"));
    roundAmountField.setDisable(!roundCheckBox.isSelected());

    // When you turn of rounding
    if (!roundCheckBox.isSelected()) {
      try {
        temporaryRoundUp = Double.parseDouble(roundAmountField.getText());
        if (temporaryRoundUp == 0.0) {
          temporaryRoundUp = 1.0;
        }
      } catch (NumberFormatException e) {
        temporaryRoundUp = 1.0;
      }
      roundAmountField.clear();
      inputValidation();
    } else {
      roundAmountField.setText(Ingredient.formatDouble(temporaryRoundUp));
    }

  }

  /**
   * Discards the changes to a given ingredient, and closes the overlay.
   */
  @FXML
  private void handleCancel() {
    appController.closeIngredientEditOverlay();
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


    ingredientNameField.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        inputValidation();
      }
    });

    measuringUnitField.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        inputValidation();
      }
    });
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
