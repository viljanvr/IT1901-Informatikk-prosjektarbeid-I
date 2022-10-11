package tacocalc.fxui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tacocalc.core.ShoppingList;

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
  private Button decreaseButton;

  @FXML
  private Button increaseButton;

  @FXML
  private Button saveButton;

  @FXML
  private Button deleteButton;

  String ingredientName;
  ShoppingList shoppingList;
  AppController appController;

  protected IngredientEditController(AppController appController) {
    this.appController = appController;
  }

  /**
   * Updates the edit screen to show the name, amount and measuring unit of a single ingredient.
   *
   * @param ingredientName name of the given ingredient
   * @param shoppingList internal object containing all ingredient objects
   */
  protected void showNewIngredient(String ingredientName, ShoppingList shoppingList) {
    this.ingredientName = ingredientName;
    this.shoppingList = shoppingList;

    ingredientNameField.setText(ingredientName);
    quantityField.setText(Integer.toString(shoppingList.getIngredientAmount(ingredientName)));

    // TODO: add logic to set measuring unit
    measuringUnitField.setText("stk");
  }

  /**
   * Decreases the amount in the quantity field when pressing the minus-button.
   */
  @FXML
  private void handleDecrease() {
    int amount = shoppingList.getIngredientAmount(ingredientName);
    shoppingList.setIngredientAmount(ingredientName, amount - 1);
    quantityField.setText(Integer.toString(amount - 1));
  }

  /**
   * Increases the amount in the quantity field when pressing the plus-button.
   */
  @FXML
  private void handleIncrease() {
    int amount = shoppingList.getIngredientAmount(ingredientName);
    shoppingList.setIngredientAmount(ingredientName, amount + 1);
    quantityField.setText(Integer.toString(amount + 1));
  }

  /**
   * Saves the changes to a given ingredient when pressing the save-button and closes the editing.
   * overlay.
   */
  @FXML
  protected void handleSave() {
    appController.closeIngredientEditOverlay();
    appController.updateIngredient(ingredientName, ingredientNameField.getText(),
        Integer.parseInt(quantityField.getText()));
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
