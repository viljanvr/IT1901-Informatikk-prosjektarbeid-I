package tacocalc.fxui;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
// import tacocalc.core.RecipeBook;
import tacocalc.data.RecipeFileHandler;

/**
 * Class that controlls the scene where the user picks the recipe.
 */
public class RecipeBookController {

  // private RecipeBook recipeBook;
  static String transferRecipe;

  @FXML
  private GridPane recipeList;

  @FXML
  private BorderPane popUpContain;

  @FXML
  private VBox container;

  @FXML
  private MFXButton createRecipeButton;

  private AddRecipeController addRecipeController;

  private Pane addRecipeOverlay;

  BoxBlur blur = new BoxBlur(30, 30, 3);

  public void initialize() {
    getRecipesFromFile();
    initAddRecipeOverlay();
  }

  /**
   * new directory Fills this with files of.
   *
   */
  private void getRecipesFromFile() {
    RecipeFileHandler fh = new RecipeFileHandler();
    fh.getAllRecipies().stream().forEach(r -> addItemToView(r.getName()));

  }

  /**
   * Adds a button with the name of the recipe to the view.
   *
   * @param recipeName the name of the recipe which will be added
   */
  private void addItemToView(String recipeName) {
    MFXButton recipeButton = new MFXButton(recipeName);
    recipeButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    recipeButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        changeToScene(recipeButton.getText());
      }

    });
    List<Node> children = recipeList.getChildren();
    if (children.size() / 2 == recipeList.getRowCount()) {
      recipeList.getRowConstraints().add(new RowConstraints(40));
    }
    int columnPosition = (children.size() % 2 == 0) ? 0 : 1;
    int rowPosition = children.size() / 2;
    recipeList.add(recipeButton, columnPosition, rowPosition);
  }

  @FXML
  private void handleCreateRecipe() {
    popUpContain.setVisible(true);
    container.setEffect(blur);
    addRecipeController.handleRecipeNameChange();
  }

  protected void closeOverlay() {
    popUpContain.setVisible(false);
    container.setEffect(null);
  }

  @FXML
  private void clickOutsideOverlayHandle(MouseEvent e) {
    if (Objects.equals(e.getPickResult().getIntersectedNode().getId(), "popUpContain")) {
      closeOverlay();
    }
  }

  /**
   * Changes scene to show a given recipe.
   *
   * @param recipeName the name of the recipie to open
   *
   * @throws IOException throws exception if specified FXML is not found
   */
  public void changeToScene(String recipeName) {
    Stage thisStage = (Stage) recipeList.getScene().getWindow();
    setTransfer(recipeName);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("ShoppingList.fxml"));
    try {
      Parent root = loader.load();
      thisStage.setScene(new Scene(root));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to set the static variable that passes data between scenes.
   *
   * @param recipeName the string that will be passed to the next scene
   */
  protected static synchronized void setTransfer(String recipeName) {
    RecipeBookController.transferRecipe = recipeName;
  }

  private void initAddRecipeOverlay() {
    addRecipeController = new AddRecipeController(this);

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddRecipePopup.fxml"));
    fxmlLoader.setController(addRecipeController);
    try {
      addRecipeOverlay = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    popUpContain.setCenter(addRecipeOverlay);
  }

  // Getters used in tests
  protected GridPane getGridPane() {
    GridPane duplicate = recipeList;
    return duplicate;
  }

  protected BorderPane getBorderPane() {
    BorderPane duplicate = popUpContain;
    return duplicate;
  }

  protected AddRecipeController getAddRecipeController() {
    return this.addRecipeController;
  }
}
