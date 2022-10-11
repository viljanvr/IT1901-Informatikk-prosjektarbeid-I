package tacocalc.fxui;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.jupiter.api.Assertions;

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

  @Test
  public void testAddIngredient() {
    clickOn("#newIngredientAmntField").write("4");
    clickOn("#newIngredientNameField").write("flinke proggere");
    Assertions.assertEquals("4", controller.getNewIngredientAmntField().getText());
    Assertions.assertEquals("flinke proggere", controller.getNewIngredientNameField().getText());
    clickOn("#addIngredient");
  }

}
