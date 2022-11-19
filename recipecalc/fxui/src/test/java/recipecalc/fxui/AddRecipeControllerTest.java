package recipecalc.fxui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import recipecalc.core.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AddRecipeControllerTest extends AppTest {
  Parent root;
  RecipeBookController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    RecipeBookController.setTransfer(new Recipe("Add-recipe-controller-test-file"));
    FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeBook.fxml"));
    Parent root = loader.load();
    stage.setScene(new Scene(root));
    controller = loader.getController();
    stage.show();
    stage.toFront();
  }

  @Test

  @DisplayName("Close-PopUp-Test")
  public void testClosePopUp() {
    clickOn("#createRecipeButton");
    Assertions.assertTrue(controller.getBorderPane().isVisible());
    clickOn("#cancelButton");
    Assertions.assertFalse(controller.getBorderPane().isVisible());
  }

  @Test

  @DisplayName("Use-Checkmark-Test")
  public void testcheckbox() {
    clickOn("#createRecipeButton");
    clickOn("#templateCheckbox");
    Assertions.assertFalse(controller.getAddRecipeController().getCheckbox().isDisable());
    clickOn("#templateCheckbox");
    Assertions.assertTrue(controller.getAddRecipeController().getCheckbox().isSelected());
  }

  @Test

  @DisplayName("Write-invalid-input-test")
  public void testInvalidInput() {
    clickOn("#createRecipeButton");
    Assertions.assertTrue(controller.getAddRecipeController().getCreateButton().isDisable());
    clickOn("#recipeNameField").write("Test");
    Assertions.assertFalse(controller.getAddRecipeController().getCreateButton().isDisable());
    clickOn("#recipeNameField").eraseText(4).write("Test-1");
    Assertions.assertFalse(controller.getAddRecipeController().getCreateButton().isDisable());
    clickOn("#recipeNameField").eraseText(6).write("!!!");
    Assertions.assertTrue(controller.getAddRecipeController().getCreateButton().isDisable());
    clickOn("#recipeNameField").eraseText(3).write(" Test");
    Assertions.assertTrue(controller.getAddRecipeController().getCreateButton().isDisable());
    clickOn("#recipeNameField").eraseText(5).write("Test ");
    Assertions.assertTrue(controller.getAddRecipeController().getCreateButton().isDisable());
  }
}
