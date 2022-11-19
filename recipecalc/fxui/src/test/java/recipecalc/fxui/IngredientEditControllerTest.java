package recipecalc.fxui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import recipecalc.core.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IngredientEditControllerTest extends AppTest {
  Parent root;
  AppController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    RecipeBookController.setTransfer(new Recipe("Ingredient-edit-controller-test-file"));
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ShoppingList.fxml"));
    Parent root = loader.load();
    stage.setScene(new Scene(root));
    controller = loader.getController();
    stage.show();
    stage.toFront();
  }

  @Test
  @DisplayName("Increase-and-decrease-amount-test")
  public void testIncreaseAndDecrease() {
    clickOn("#editButton");
    addIngredient("2", "paprika", "stk");
    clickOn(getIngredientEditButton(0, this.controller));
    clickOn("#decreaseButton");
    clickOn("#saveButton");
    Assertions.assertEquals("4 stk paprika", getIngredientText(0, this.controller));
    clickOn("#editButton");
    clickOn(getIngredientEditButton(0, this.controller));
    clickOn("#increaseButton");
    clickOn("#increaseButton");
    clickOn("#saveButton");
    Assertions.assertEquals("12 stk paprika", getIngredientText(0, this.controller));
  }

  @Test
  @DisplayName("Change-ingredient-name-test")
  public void testIngredientNameChange() {
    clickOn("#editButton");
    addIngredient("1", "bacon", "stk");
    clickOn(getIngredientEditButton(0, this.controller));
    clickOn("#ingredientNameField").eraseText(5).write("bacon terninger");
    clickOn("#ingredientNameField").clickOn("#saveButton");
    Assertions.assertEquals("4 stk bacon terninger", getIngredientText(0, this.controller));
  }

  @Test
  @DisplayName("Delete-ingredient-test")
  public void deleteIngredientTest() {
    clickOn("#editButton");
    addIngredient("1", "rømme", "dl");
    clickOn(getIngredientEditButton(0, this.controller));
    clickOn("#deleteIngredientButton");
    Assertions.assertEquals(0, controller.getIngredientViewStream().count());
  }

  @Test

  @DisplayName("Test-input-validation")
  public void testValidation() {
    clickOn("#editButton");
    addIngredient("1", "rømme", "dl");
    clickOn(getIngredientEditButton(0, this.controller));
    Assertions.assertFalse(controller.getIngredientEditController().getSaveButton().isDisable());
    clickOn("#quantityField").eraseText(3).write("invalid");
    Assertions.assertTrue(controller.getIngredientEditController().getSaveButton().isDisable());
    clickOn("#roundAmountField").eraseText(3).write("invalid");
    Assertions.assertTrue(controller.getIngredientEditController().getSaveButton().isDisable());
    clickOn("#quantityField").eraseText(7).write("3.5");
    Assertions.assertTrue(controller.getIngredientEditController().getSaveButton().isDisable());
  }
}
