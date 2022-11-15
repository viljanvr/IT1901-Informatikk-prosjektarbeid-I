package tacocalc.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

  // TODO: Add tests for RecipeBook

  @Test

  @DisplayName("Test add item to view")
  public void TestCreteRecipe() {
    clickOn("#createRecipeButton");
    clickOn("#recipeNameField").write("TestRecipe");
    clickOn("#createButton");
    clickOn("#editButton");
    addIngredient("0.5", "agurk", "stk");
    clickOn("#backButton");
    Assertions.assertFalse(controller.getGridPane().getChildren().stream()
        .anyMatch(n -> ((Button) n).getText() == "TestRecipe"));
  }


}
