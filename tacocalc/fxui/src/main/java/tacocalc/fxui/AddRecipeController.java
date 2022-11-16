package tacocalc.fxui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tacocalc.data.RecipeFileHandler;

/**
 * Controller for overlay appearing when pressing the "Create new recipe"-button from the recipe
 * overview screen.
 */
public class AddRecipeController {

  @FXML
  private MFXTextField recipeNameField;

  @FXML
  private Text errorMessage;

  @FXML
  private Text createFromTemplateText;

  @FXML
  private MFXButton createButton;

  @FXML
  private MFXButton cancelButton;

  @FXML
  private MFXCheckbox templateCheckbox;

  @FXML
  private MFXComboBox<String> templateComboBox;

  private RecipeBookController recipeBookController;

  protected AddRecipeController(RecipeBookController recipeBookController) {
    this.recipeBookController = recipeBookController;
  }

  /**
   * Initializes the application.
   */
  public void initialize() {
    recipeNameField.addEventFilter(KeyEvent.ANY, e -> {
      handleRecipeNameChange();
    });
  }

  @FXML
  private void handleCreate() {
    recipeBookController.changeToScene(recipeNameField.getText());
  }

  @FXML
  private void handleCancel() {
    recipeBookController.closeOverlay();
  }

  @FXML
  private void handleTemplateCheckbox() {
    createFromTemplateText
        .setFill(templateCheckbox.isSelected() ? Color.WHITE : Color.web("#ababab"));
    templateComboBox.setDisable(!templateCheckbox.isSelected());

  }

  @FXML
  private void handleTemplateChange() {

  }

  protected MFXCheckbox getCheckbox() {
    MFXCheckbox duplicate = templateCheckbox;
    return duplicate;
  }

  protected void handleRecipeNameChange() {
    if (RecipeFileHandler.validFileName(recipeNameField.getText())) {
      // If input is valid
      createButton.setDisable(false);
      errorMessage.setVisible(false);
    } else if (recipeNameField.getText().isEmpty()) {
      // If input is empty
      createButton.setDisable(true);
      errorMessage.setVisible(false);
    } else {
      // If input contains invalid characters
      createButton.setDisable(true);
      errorMessage.setVisible(true);
    }
  }

}
