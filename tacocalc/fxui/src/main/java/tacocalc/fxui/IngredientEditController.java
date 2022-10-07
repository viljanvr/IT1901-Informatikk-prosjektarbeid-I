package tacocalc.fxui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tacocalc.core.ShoppingList;

public class IngredientEditController {

  @FXML
  private TextField ingredientNameField, measuringUnitField, quantityField;

  @FXML
  private Button decreaseButton, increaseButton, saveButton, deleteButton;

  String ingredientName;
  ShoppingList shoppingList;
  AppController appController;

  protected IngredientEditController(AppController appController) {
    this.appController = appController;
  }


  protected void showNewIngredient(String ingredientName, ShoppingList shoppingList) {
    this.ingredientName = ingredientName;
    this.shoppingList = shoppingList;

    ingredientNameField.setText(ingredientName);
    quantityField.setText(Integer.toString(shoppingList.getIngredientAmount(ingredientName)));

    // TODO: add logic to set measuring unit
    measuringUnitField.setText("stk");
  }

  @FXML
  private void handleDecrease() {
    int amount = shoppingList.getIngredientAmount(ingredientName);
    shoppingList.setIngredientAmount(ingredientName, amount - 1);
    quantityField.setText(Integer.toString(amount - 1));
  }

  @FXML
  private void handleIncrease() {
    int amount = shoppingList.getIngredientAmount(ingredientName);
    shoppingList.setIngredientAmount(ingredientName, amount + 1);
    quantityField.setText(Integer.toString(amount + 1));
  }

  @FXML
  protected void handleSave() {
    appController.handleGoBack();
    appController.updateIngredient(ingredientName, ingredientNameField.getText(),
        Integer.parseInt(quantityField.getText()));
  }

  @FXML
  private void handleDelete() {
    appController.handleGoBack();
    appController.handleDeleteIngredient(ingredientName);

    // TODO: implement deleting

  }


}
