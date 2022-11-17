package recipecalc.fxui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import recipecalc.core.Recipe;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecipeBookControllerTest extends AppTest {
  Parent root;
  RecipeBookController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    RecipeBookController.setTransfer(new Recipe("Test-file"));
    FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeBook.fxml"));
    Parent root = loader.load();
    stage.setScene(new Scene(root));
    controller = loader.getController();
    stage.show();
    stage.toFront();
  }

  @Test
  @DisplayName("Test-add-item-to-view")
  public void testCreateRecipe() {
    create();
    Assertions.assertTrue(isInGridpane("TestRecipe"));
  }

  private void create() {
    clickOn("#createRecipeButton");
    clickOn("#recipeNameField").write("TestRecipe");
    clickOn("#createButton");
    clickOn("#editButton");
    addIngredient("0.5", "agurk", "stk");
    clickOn("#backButton");
  }

  private boolean isInGridpane(String predicate) {
    List<Node> children = controller.getGridPane().getChildren();
    for (Node child : children) {
      if (((Button) child).getText().equals(predicate)) {
        return true;
      }
    }
    return false;
  }
}
