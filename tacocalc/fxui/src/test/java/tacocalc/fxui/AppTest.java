package tacocalc.fxui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tacocalc.core.Ingredient;
import tacocalc.core.Recipe;
import tacocalc.data.TacoCalcFileHandler;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

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

  // method is used to add an ingredient in other tests

  @Test
  @DisplayName("Test adding new ingredients to view")
  public void addNewIngredientTest() {
    clickOn("#newIngredientAmntField").write("4");
    clickOn("#newIngredientNameField").write("agurk");
    // Gives our testTaco file a name
    // If no name is given, it will be made a .json file with no name
    // clickOn("#nameField").write("TestTaco");
    // Checks if textfield contains expected text
    Assertions.assertEquals("4", controller.getNewIngredientAmntField().getText());
    Assertions.assertEquals("agurk", controller.getNewIngredientNameField().getText());
    clickOn("#addIngredient");
    Assertions.assertEquals("4x agurk", getIngredientTextField(0));

    // Checks that the input fiels are cleared after adding an ingredient
    Assertions.assertEquals("", controller.getNewIngredientAmntField().getText());
    Assertions.assertEquals("", controller.getNewIngredientNameField().getText());

    // Cheks that a second ingredient is added at the correct position
    clickOn("#newIngredientAmntField").write("5");
    clickOn("#newIngredientNameField").write("tomtat");
    clickOn("#addIngredient");
    Assertions.assertEquals("5x tomtat", getIngredientTextField(1));
  }

  // Tests the error message for wrong input in integer field
  @Test
  @DisplayName("Test that adding an ingredient with an invalid amount doesn't do")
  public void testInvalidAmount() {
    addIngredient("NotAnInteger", "Should give popup error");
    Assertions.assertEquals(0, controller.getIngredientViewStream().count());
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

    Assertions.assertEquals("5x tomat", getIngredientTextField(0));
    Assertions.assertEquals("2x avocado", getIngredientTextField(1));
  }

  @Test
  @DisplayName("Test deleting an element from the view")
  public void deleteIngredientTest() {
    addIngredient("1", "rømme");
    clickOn("#editButton");
    clickOn(getIngredientEditButton(0));
    clickOn("#deleteButton");
    Assertions.assertEquals(0, controller.getIngredientViewStream().count());
    // TODO: Maybe test checkbox
  }

  private void addIngredient(String amount, String name) {
    clickOn("#newIngredientAmntField").write(amount);
    clickOn("#newIngredientNameField").write(name);
    clickOn("#addIngredient");
  }

  private String getIngredientTextField(int index) {
    return ((TextField) controller.getIngredientViewStream().skip(3 * index + 1).findAny().get())
        .getText();
  }

  private Button getIngredientEditButton(int index) {
    return ((Button) controller.getIngredientViewStream().skip(3 * index + 2).findAny().get());
  }

  private void createTestFile() {
    TacoCalcFileHandler fh = new TacoCalcFileHandler();
    fh.write(new Recipe(new Ingredient("tomat", 5), new Ingredient("avocado", 2)), "testFile");
  }
}
