package tacocalc.fxui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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

  RecipeBookController recipeBookController;

  public AddRecipeController(RecipeBookController recipeBookController) {
    this.recipeBookController = recipeBookController;
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

}
