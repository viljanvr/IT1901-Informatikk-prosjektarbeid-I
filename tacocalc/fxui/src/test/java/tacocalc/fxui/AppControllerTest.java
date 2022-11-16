package tacocalc.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import tacocalc.core.Ingredient;
import tacocalc.core.Recipe;
import tacocalc.data.RecipeFileHandler;

public class AppControllerTest extends AppTest {
  Parent root;
  AppController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ShoppingList.fxml"));
    Parent root = loader.load();
    stage.setScene(new Scene(root));
    controller = loader.getController();
    stage.show();
    stage.toFront();
  }

  @Test

  @DisplayName("Add-new-ingredient-to-view-test")
  public void addNewIngredientTest() {
    clickOn("#editButton");
    clickOn("#newIngredientAmntField").write("2");
    clickOn("#newIngredientNameField").write("agurk");
    clickOn("#newMeasurementField").write("stk");

    // Checks if textfield contains expected text
    Assertions.assertEquals("2", controller.getNewIngredientAmntField().getText());
    Assertions.assertEquals("agurk", controller.getNewIngredientNameField().getText());
    clickOn("#addIngredient");
    Assertions.assertEquals("8 stk agurk", getIngredientText(0, this.controller));

    // Checks that the input fiels are cleared after adding an ingredient
    Assertions.assertEquals("", controller.getNewIngredientAmntField().getText());
    Assertions.assertEquals("", controller.getNewIngredientNameField().getText());

    // Cheks that a second ingredient is added at the correct position
    clickOn("#newIngredientAmntField").write("1");
    clickOn("#newIngredientNameField").write("tomat");
    clickOn("#newMeasurementField").write("stk");
    clickOn("#addIngredient");
    Assertions.assertEquals("4 stk tomat", getIngredientText(1, this.controller));
  }

  // Tests the error message for wrong input in integer field

  @Test

  @DisplayName("Invalid-ingredient-amount-test")
  public void testInvalidAmount() {
    clickOn("#editButton");
    addIngredient("NotAnInteger", "Should give popup error", "Something stupid");
    Assertions.assertEquals(0, controller.getIngredientViewStream().count());
  }

  // Tests other important button with a lot of logic

  @Test

  @DisplayName("Add-duplicate-ingredient-test")
  public void testDuplicateIngredient() {
    clickOn("#editButton");
    addIngredient("1", "ost", "stk");
    addIngredient("2", "ost", "stk");
    addIngredient("2", "OsT", "stk");
    Assertions.assertEquals(3, controller.getIngredientViewStream().count());
  }
}
