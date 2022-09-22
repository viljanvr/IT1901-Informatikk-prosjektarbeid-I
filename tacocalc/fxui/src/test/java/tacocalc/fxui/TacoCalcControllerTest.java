package tacocalc.fxui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class TacoCalcControllerTest extends ApplicationTest{

    AppController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("ShoppingList.fxml"));
        final Parent root = loader.load();
        this.controller = new AppController();
        stage.setScene(new Scene(root));
        stage.show();
     }

    @Test
    public void testAdditem() {
        clickOn("#ingredientAmntField").write("3");
        clickOn("#ingredientNameField").write("tomat");
        clickOn("#addingredient");
    }
}
