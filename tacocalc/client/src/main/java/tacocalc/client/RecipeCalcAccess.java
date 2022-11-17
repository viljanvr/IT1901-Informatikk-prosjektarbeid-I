package tacocalc.client;

import java.util.List;
import tacocalc.core.Ingredient;
import tacocalc.core.Recipe;

/**
 * Interface with methods needed in the fxui model.
 */
public interface RecipeCalcAccess {

  public void changeRecipeName(Recipe r, String name);

  public void setBought(Recipe recipe, String ingredientName, Boolean bought);

  public void deleteIngredient(Recipe recipe, String ingredientName);

  public void updateIngredient(Recipe recipe, String ingredient, String newIngredientName,
      Double perPersonAmount, Double roundUpTo, String measuringUnit);

  public void addIngredient(Recipe recipe, Ingredient ingredient);

  public List<Recipe> getAllRecipes();

  public void addRecipe(Recipe r);
}
