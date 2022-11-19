package recipecalc.client;

import java.util.HashMap;
import java.util.List;
import recipecalc.core.Ingredient;
import recipecalc.core.Recipe;

/**
 * Interface with methods needed in the fxui model.
 */
public interface RecipeCalcAccess {

  public List<Recipe> getAllRecipes();

  public List<Recipe> getAllTemplates();

  public boolean addRecipe(Recipe recipe);

  public boolean deleteRecipe(String recipeName);

  public boolean changeRecipeName(String oldRecipeName, String newRecipeName);

  public boolean addIngredient(String recipeName, Ingredient ingredient);

  public boolean deleteIngredient(String recipeName, String ingredientName);

  public boolean setBought(String recipeName, String ingredientName, Boolean bought);

  public HashMap<String, Boolean> updateIngredient(String recipeName, String oldIngredientName,
      String newIngredientName, Double perPersonAmount, Double roundUpTo, String measuringUnit);

}
