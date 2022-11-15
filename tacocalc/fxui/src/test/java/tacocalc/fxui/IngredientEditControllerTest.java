package tacocalc.fxui;

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

public class IngredientEditControllerTest extends ApplicationTest implements AppTest {
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

  // Tests Functions in the ingredient edit controller

  @Test

  @DisplayName("Test that you can increase and decrease the amount of an ingredient")
  public void testIncreaseAndDecrease() {
    clickOn("#editButton");
    addIngredient("2", "paprika", "stk");
    clickOn(getIngredientEditButton(0));
    clickOn("#decreaseButton");
    clickOn("#saveButton");
    Assertions.assertEquals("4 stk paprika", getIngredientText(0));
    clickOn("#editButton");
    clickOn(getIngredientEditButton(0));
    clickOn("#increaseButton");
    clickOn("#increaseButton");
    clickOn("#saveButton");
    Assertions.assertEquals("12 stk paprika", getIngredientText(0));
  }

  @Test

  @DisplayName("Test that you can change the name of an ingredient")
  public void testIngredientNameChange() {
    clickOn("#editButton");
    addIngredient("1", "bacon", "stk");
    clickOn(getIngredientEditButton(0));
    clickOn("#ingredientNameField").eraseText(5).write("bacon terninger");
    clickOn("#ingredientNameField").clickOn("#saveButton");
    Assertions.assertEquals("4 stk bacon terninger", getIngredientText(0));
  }

  @Test

  @DisplayName("Test deleting an element from the view")
  public void deleteIngredientTest() {
    clickOn("#editButton");
    addIngredient("1", "rømme", "dl");
    clickOn(getIngredientEditButton(0));
    clickOn("#deleteButton");
    Assertions.assertEquals(0, controller.getIngredientViewStream().count());
    // TODO: Maybe test checkbox
  }

  @Override
  public void addIngredient(String amount, String name, String measuringUnit) {
    clickOn("#newIngredientAmntField").write(amount);
    clickOn("#newIngredientNameField").write(name);
    clickOn("#newMeasurementField").write(measuringUnit);
    clickOn("#addIngredient");
  }

  @Override
  public String getIngredientText(int index) {
    return ((Text) controller.getIngredientViewStream().skip(3 * index + 1).findAny().get())
        .getText();
  }

  @Override
  public Button getIngredientEditButton(int index) {
    return ((Button) controller.getIngredientViewStream().skip(3 * index + 2).findAny().get());
  }

  @Override
  public void createTestFile() {

  }

}
