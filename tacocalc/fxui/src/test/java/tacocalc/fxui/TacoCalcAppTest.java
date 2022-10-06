package tacocalc.fxui;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.jupiter.api.Assertions;

public class TacoCalcAppTest extends ApplicationTest{

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
        clickOn("#ingredientAmntField").write("4");
        clickOn("#ingredientNameField").write("agurk");
        // Gives our testTaco file a name
        // If no name is given, it will be made a .json file with no name
        clickOn("#nameField").write("TestTaco");
        // Checks if textfield contains expected text
        Assertions.assertEquals("4", controller.getIngredientAmntField().getText());
        Assertions.assertEquals("agurk", controller.getIngredientNameField().getText());
        clickOn("#addIngredient");
    }

    @Test
    public void testErrorMessage() {
        addIngredient();
        clickOn("#ingredientAmntField").write("NotAnInteger");
        clickOn("#ingredientNameField").write("Should give popup error");
        clickOn("#addIngredient");
    }

    @Test
    public void testDeleteIngredient() {
        addIngredient();
        clickOn("#editButton");
    }
}
