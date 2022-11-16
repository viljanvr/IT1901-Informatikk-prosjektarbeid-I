package tacocalc.fxui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecipeBookControllerTest extends AppTest {
  Parent root;
  RecipeBookController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeBook.fxml"));
    Parent root = loader.load();
    stage.setScene(new Scene(root));
    controller = loader.getController();
    stage.show();
    stage.toFront();
  }

  @Test

  @DisplayName("Test-add-item-to-view")
  public void testCreteRecipe() {
    clickOn("#createRecipeButton");
    clickOn("#recipeNameField").write("TestRecipe");
    clickOn("#createButton");
    clickOn("#editButton");
    addIngredient("0.5", "agurk", "stk");
    clickOn("#backButton");
    Assertions.assertFalse(controller.getGridPane().getChildren().stream()
        .anyMatch(n -> ((Button) n).getText() == "TestRecipe"));
  }

  @Test

  @DisplayName("Test-popup-elements")
  public void testClosePopUp() {
    clickOn("#createRecipeButton");
    Assertions.assertTrue(controller.getBorderPane().isVisible());
    clickOn("#templateCheckbox");
    Assertions.assertFalse(controller.getAddRecipeController().getCheckbox().isDisable());
    clickOn("#templateCheckbox");
    Assertions.assertTrue(controller.getAddRecipeController().getCheckbox().isSelected());
    clickOn("#cancelButton");
    Assertions.assertFalse(controller.getBorderPane().isVisible());
  }
}
