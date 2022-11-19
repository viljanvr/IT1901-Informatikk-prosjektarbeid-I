package recipecalc.fxui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import recipecalc.core.Recipe;
import recipecalc.data.RecipeFileHandler;

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

  /**
   * Changes the scene to the new Recipe once it is created.
   */
  @FXML
  private void handleCreate() {
    recipeBookController.changeToScene(new Recipe(recipeNameField.getText()));
  }

  /**
   * Closes the overlay when a new Recipe is not created.
   */
  @FXML
  private void handleCancel() {
    recipeBookController.closeOverlay();
  }

  /**
   * Updates the combobox if checkbox is pressed/unpressed.
   */
  @FXML
  private void handleTemplateCheckbox() {
    createFromTemplateText
        .setFill(templateCheckbox.isSelected() ? Color.WHITE : Color.web("#ababab"));
    templateComboBox.setDisable(!templateCheckbox.isSelected());

  }

  /**
   * Validates that the template exists and delegates.
   */
  @FXML
  private void handleTemplateChange() {

  }

  /**
   * Validity checks a textfield.
   */
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

  /**
   * Method used in testing. Gets a checkbox.
   *
   * @return A checkbox
   */
  protected MFXCheckbox getCheckbox() {
    MFXCheckbox duplicate = templateCheckbox;
    return duplicate;
  }

  /**
   * Method used in testing. Gets a button.
   * 
   * @return A button
   */
  protected Button getCreateButton() {
    return this.createButton;
  }

}
