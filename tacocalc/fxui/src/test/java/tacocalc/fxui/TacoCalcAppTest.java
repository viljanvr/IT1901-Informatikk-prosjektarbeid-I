package tacocalc.fxui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class TacoCalcAppTest extends ApplicationTest {
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
  private void addIngredient() {
    clickOn("#newIngredientAmntField").write("4");
    clickOn("#newIngredientNameField").write("agurk");
    // Gives our testTaco file a name
    // If no name is given, it will be made a .json file with no name
    clickOn("#nameField").write("TestTaco");
    // Checks if textfield contains expected text
    Assertions.assertEquals("4", controller.getNewIngredientAmntField().getText());
    Assertions.assertEquals("agurk", controller.getNewIngredientNameField().getText());
    clickOn("#addIngredient");
    Assertions.assertEquals("4x agurk", getIngredientTextField(0));

  }

  // Tests the error message for wrong input in integer field
  @Test
  public void testErrorMessage() {
    addIngredient();
    Assertions.assertEquals("4x agurk", getIngredientTextField(0));
    clickOn("#newIngredientAmntField").write("NotAnInteger");
    clickOn("#newIngredientNameField").write("Should give popup error");
    clickOn("#addIngredient");
  }

  // Tests other important button with a lot of logic
  @Test
  public void testImportantButtons() {
    clickOn("#nameField").write("TestTaco");
    clickOn("#loadButton");
    Assertions.assertEquals("4x agurk", getIngredientTextField(0));
    clickOn("#editButton");
    clickOn(getIngredientEditButton(0));
    clickOn("#deleteButton");
    Assertions.assertEquals(0, controller.getIngredientViewStream().count());
    // TODO: Maybe test checkbox
  }

  private String getIngredientTextField(int index) {
    return ((TextField) controller.getIngredientViewStream().skip(3 * index + 1).findAny().get())
        .getText();
  }

  private Button getIngredientEditButton(int index) {
    return ((Button) controller.getIngredientViewStream().skip(3 * index + 2).findAny().get());
  }
}
