package tacocalc.fxui;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.testfx.framework.junit5.ApplicationTest;
import tacocalc.core.Ingredient;
import tacocalc.core.Recipe;
import tacocalc.data.RecipeFileHandler;

/**
 * AppTest
 */
public abstract class AppTest extends ApplicationTest {

  public void addIngredient(String amount, String name, String measuringUnit) {
    clickOn("#newIngredientAmntField").write(amount);
    clickOn("#newIngredientNameField").write(name);
    clickOn("#newMeasurementField").write(measuringUnit);
    clickOn("#addIngredient");
  }

  public String getIngredientText(int index, AppController controller) {
    return ((Text) controller.getIngredientViewStream().skip(3 * index + 1).findAny().get())
        .getText();
  }

  public Button getIngredientEditButton(int index, AppController controller) {
    return ((Button) controller.getIngredientViewStream().skip(3 * index + 2).findAny().get());
  }

  public CheckBox getIngredientCheckBox(int index, AppController controller) {
    return ((CheckBox) controller.getIngredientViewStream().skip(3 * index + 0).findAny().get());
  }

  public void createTestFile() {
    RecipeFileHandler.write(new Recipe("testFile", new Ingredient("tomat", 2.0, "default"),
        new Ingredient("avocado", 1.0, "default")));
  }

  @BeforeEach
  public void setUp(TestInfo testInfo) {
    File testRecipe = new File(System.getProperty("user.home") + "/recipecalc/test/Test-file.json");
    testRecipe.delete();
    RecipeFileHandler.setTestMode(true);
  }

}
