package tacocalc.fxui;

import java.io.File;
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
import tacocalc.core.RecipeBook;

public class RecipeBookController {

  private RecipeBook recipeBook;
  public static String transferRecipe;

  @FXML
  private GridPane recipeList;

  public void initialize() {
    getRecipesFromFile();
  }

  private void getRecipesFromFile() {
    File dir = new File("../data/src/main/resources/");
    File[] listOfRecipes = dir.listFiles();
    List<String> recipeNames = Arrays.stream(listOfRecipes).map(f -> f.getName().split("\\.")[0])
        .collect(Collectors.toList());
    for (String recipeName : recipeNames) {
      addItemToView(recipeName);

    }
  }

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
    int rowPosition = (children.size() % 2 == 0) ? recipeList.getRowCount() : recipeList.getRowCount() - 1;
    recipeList.add(recipeButton, columnPosition, rowPosition);

  }

  public void changeToScene(String recipeName, Stage thisStage) throws IOException {
    RecipeBookController.transferRecipe = recipeName;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ShoppingList.fxml"));
    Parent root = loader.load();

    thisStage.setScene(new Scene(root));

  }
}
