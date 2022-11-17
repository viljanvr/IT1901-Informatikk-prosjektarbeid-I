package recipecalc.fxui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Starting class for application.
 */
public class App extends Application {
  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(this.getClass().getResource("RecipeBook.fxml"));
    primaryStage.setScene(new Scene(root));
    primaryStage.setTitle("Recipe Calculator");
    primaryStage.show();
  }

}
