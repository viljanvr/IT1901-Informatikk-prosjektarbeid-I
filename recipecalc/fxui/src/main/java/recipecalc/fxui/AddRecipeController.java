package recipecalc.fxui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import recipecalc.client.RemoteRecipeCalcAccess;
import recipecalc.core.Recipe;

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

  private RemoteRecipeCalcAccess rrca = new RemoteRecipeCalcAccess("http://localhost", 8080);

  private List<Recipe> templates;

  protected AddRecipeController(RecipeBookController recipeBookController) {
    this.recipeBookController = recipeBookController;
  }

  /**
   * Initializes the application.
   */
  public void initialize() {
    recipeNameField.addEventFilter(KeyEvent.ANY, e -> {
      inputValidation();
    });
  }

  protected void loadTemplates() {
    templates = rrca.getAllTemplates();
    templateComboBox.setItems(templates.stream().map(r -> r.getName())
        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
  }

  @FXML
  private void handleCreate() {
    if (templateCheckbox.isSelected()) {
      Recipe r = templates.stream().filter(t -> t.getName().equals(templateComboBox.getValue()))
          .findFirst().get();
      r.setName(recipeNameField.getText());
      rrca.addRecipe(r);
      recipeBookController.changeToScene(r);
    } else {
      recipeBookController.changeToScene(new Recipe(recipeNameField.getText()));
    }
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
    templateComboBox.clearSelection();
    inputValidation();
  }

  @FXML
  private void handleTemplateChange() {
    inputValidation();
  }

  protected void inputValidation() {
    Boolean nameError = !Recipe.isValidRecipeName(recipeNameField.getText());
    Boolean nameEmpty = recipeNameField.getText().isEmpty();
    Boolean templateError = templateCheckbox.isSelected() && templateComboBox.getValue() == null;

    createButton.setDisable(nameError || templateError);

    errorMessage.setVisible((nameError && !nameEmpty) || templateError);
    errorMessage.setText(
        nameError && !nameEmpty && templateError ? "The name is unvalid and no template is chosen"
            : nameError && !nameEmpty ? "The name is unvalid" : "No Template is chosen");
  }

  protected MFXComboBox<String> getTemplateComboBox() {
    MFXComboBox<String> duplicate = templateComboBox;
    return duplicate;
  }

  protected Button getCreateButton() {
    return this.createButton;
  }

}
