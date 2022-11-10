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
import tacocalc.data.TacoCalcFileHandler;

public class AppTest extends ApplicationTest {
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

  // TODO: Implement measuring unit into test (per now it only has "Default")

  // method is used to add an ingredient in other tests

  @Test

  @DisplayName("Test adding new ingredients to view")
  public void addNewIngredientTest() {
    clickOn("#editButton");
    clickOn("#newIngredientAmntField").write("2");
    clickOn("#newIngredientNameField").write("agurk");
    clickOn("#newMeasurementField").write("stk");

    // Checks if textfield contains expected text
    Assertions.assertEquals("2", controller.getNewIngredientAmntField().getText());
    Assertions.assertEquals("agurk", controller.getNewIngredientNameField().getText());
    clickOn("#addIngredient");
    Assertions.assertEquals("8 stk agurk", getIngredientText(0));

    // Checks that the input fiels are cleared after adding an ingredient
    Assertions.assertEquals("", controller.getNewIngredientAmntField().getText());
    Assertions.assertEquals("", controller.getNewIngredientNameField().getText());

    // Cheks that a second ingredient is added at the correct position
    clickOn("#newIngredientAmntField").write("1");
    clickOn("#newIngredientNameField").write("tomat");
    clickOn("#newMeasurementField").write("stk");
    clickOn("#addIngredient");
    Assertions.assertEquals("4 stk tomat", getIngredientText(1));
  }

  // Tests the error message for wrong input in integer field

  @Test

  @DisplayName("Test that adding an ingredient with an invalid amount doesn't do")
  public void testInvalidAmount() {
    clickOn("#editButton");
    addIngredient("NotAnInteger", "Should give popup error", "Something stupid");
    Assertions.assertEquals(0, controller.getIngredientViewStream().count());
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

  // Tests other important button with a lot of logic

  @Test

  @DisplayName("Test that all items is added to view when loading a file")
  public void testLoadFile() {
    createTestFile();

    // Check that the view is empty
    assertEquals(0, controller.getIngredientViewStream().count());

    clickOn("#nameField").write("testFile");
    clickOn("#loadButton");

    Assertions.assertEquals("8 default tomat", getIngredientText(0));
    Assertions.assertEquals("4 default avocado", getIngredientText(1));
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

  // TODO: Skulle vi ha
  // @Test
  // @DisplayName("Test that it is not possible to create a duplicate of an ingredient ")
  // public void testDuplicate() {
  // clickOn("#editButton");
  // addIngredient("1", "ost", "default");
  // addIngredient("4", "ost", "default");
  // Assertions.assertEquals(3, controller.getIngredientViewStream().count());
  // // Test case sensitivity
  // addIngredient("3", "OsT", "default");
  // Assertions.assertEquals(3, controller.getIngredientViewStream().count());
  // }

  // TODO: Make edge-case tests
  // What happens if view fills up?
  // Really long name on ingredient?


  private void addIngredient(String amount, String name, String measuringUnit) {
    clickOn("#newIngredientAmntField").write(amount);
    clickOn("#newIngredientNameField").write(name);
    clickOn("#newMeasurementField").write(measuringUnit);
    clickOn("#addIngredient");
  }

  private String getIngredientText(int index) {
    return ((Text) controller.getIngredientViewStream().skip(3 * index + 1).findAny().get())
        .getText();
  }

  private Button getIngredientEditButton(int index) {
    return ((Button) controller.getIngredientViewStream().skip(3 * index + 2).findAny().get());
  }

  private void createTestFile() {
    TacoCalcFileHandler fh = new TacoCalcFileHandler();
    fh.write(new Recipe(new Ingredient("tomat", 2.0, "default"),
        new Ingredient("avocado", 1.0, "default")), "testFile");
  }

}
