package tacocalc.fxui;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
// import tacocalc.core.RecipeBook;

/**
 * Class that controlls the scene where the user picks the recipe.
 */
public class RecipeBookController {

  // private RecipeBook recipeBook;
  static String transferRecipe;

  @FXML
  private GridPane recipeList;

  public void initialize() {
    getRecipesFromFile();
  }

  /**
   * new directory Fills this with files of.
   *
   */
  private void getRecipesFromFile() {
    File dir = new File("../data/src/main/resources/");

    FilenameFilter filter = new FilenameFilter() {
      @Override
      public boolean accept(File f, String name) {
        return name.endsWith(".json");
      }
    };

    File[] listOfRecipes = dir.listFiles(filter);
    List<String> recipeNames = Arrays.stream(listOfRecipes).map(f -> f.getName().split("\\.")[0])
        .collect(Collectors.toList());
    for (String recipeName : recipeNames) {
      addItemToView(recipeName);

    }
  }

  /**
   * Adds a button with the name of the recipe to the view.
   *
   * @param recipeName the name of the recipe which will be added
   */
  private void addItemToView(String recipeName) {
    Button recipeButton = new Button(recipeName);
    recipeButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    recipeButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        try {
          Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          changeToScene(recipeButton.getText(), thisStage);
        } catch (IOException e) {
          e.printStackTrace();
        }

      }

    });
    List<Node> children = recipeList.getChildren();
    int columnPosition = (children.size() % 2 == 0) ? 0 : 1;
    int rowPosition =

        (children.size() % 2 == 0) ? recipeList.getRowCount() : recipeList.getRowCount() - 1;
    recipeList.add(recipeButton, columnPosition, rowPosition);

  }

  /**
   * Changes scene to show a given recipe.
   *
   * @param recipeName the name of the recipie to open
   *
   * @param thisStage the current window
   *
   * @throws IOException throws exception if specified FXML is not found
   */
  public void changeToScene(String recipeName, Stage thisStage) throws IOException {
    setTransfer(recipeName);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ShoppingList.fxml"));
    Parent root = loader.load();

    thisStage.setScene(new Scene(root));

  }

  /**
   * Method to set the static variable that passes data between scenes.
   *
   * @param recipeName the string that will be passed to the next scene
   */
  private static synchronized void setTransfer(String recipeName) {
    RecipeBookController.transferRecipe = recipeName;
  }
}
