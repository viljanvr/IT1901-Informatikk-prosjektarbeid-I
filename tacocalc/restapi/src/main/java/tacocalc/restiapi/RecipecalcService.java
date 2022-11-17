package tacocalc.restiapi;

import java.util.List;
import org.springframework.stereotype.Service;
import tacocalc.core.Ingredient;
import tacocalc.core.Recipe;
import tacocalc.data.RecipeFileHandler;

/**
 * Service handler for REST API.
 */

@Service
public class RecipecalcService {

  public RecipecalcService() {}

  /**
   * Returns a list of all recipes saved to file. If no recipes are saved, a default recipe is
   * returned.
   *
   * @return List of recipe objects
   */
  protected List<Recipe> getAllRecipes() {
    List<Recipe> list = RecipeFileHandler.getAllRecipies();
    if (list.isEmpty()) {
      list.add(new Recipe("test", new Ingredient("avocado", 1.0, "stk")));
      list.add(new Recipe("test2", new Ingredient("bananer", 2.0, "stk")));
    }
    return list;
  }

  protected Recipe getRecipe(String name) {
    return RecipeFileHandler.readRecipe(name);
  }

  protected boolean setBought(boolean bought, String recipeName, String ingredientName) {
    Recipe r = RecipeFileHandler.readRecipe(recipeName);
    r.setBought(ingredientName, bought);
    return RecipeFileHandler.write(r);
  }

  protected boolean addRecipe(Recipe r) {
    return RecipeFileHandler.write(r);
  }

  protected boolean removeRecipe(String name) {
    return RecipeFileHandler.deleteFile(name);
  }


  protected void setUnit(String measuringUnit, String recipeName, String ingredientName) {
    Recipe r = RecipeFileHandler.readRecipe(recipeName);
    r.setIngredientMeasurement(ingredientName, measuringUnit);
    RecipeFileHandler.write(r);
  }



}
