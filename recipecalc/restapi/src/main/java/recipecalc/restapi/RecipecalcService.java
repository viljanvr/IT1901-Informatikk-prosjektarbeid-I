package recipecalc.restapi;

import java.util.List;
import org.springframework.stereotype.Service;
import recipecalc.core.Ingredient;
import recipecalc.core.Recipe;
import recipecalc.data.RecipeFileHandler;

/**
 * Service handler for REST API.
 */

@Service
public class RecipecalcService {

  public RecipecalcService() {}

  /**
   * Returns a list of all Recipes saved to file. If no Recipes are saved, a default Recipe is
   * returned.
   *
   * @return List of Recipe objects
   */
  protected List<Recipe> getAllRecipes() {
    List<Recipe> list = RecipeFileHandler.getAllRecipies();
    if (list.isEmpty()) {
      list.add(new Recipe("test", new Ingredient("avocado", 1.0, "stk")));
      list.add(new Recipe("test2", new Ingredient("bananer", 2.0, "stk")));
    }
    return list;
  }

  /**
   * Gets a recipe by name.
   *
   * @param name Name of the Recipe to get
   * @return The recipe
   */
  protected Recipe getRecipe(String name) {
    return RecipeFileHandler.readRecipe(name);
  }

  /**
   * Adds a Recipe to the API.
   *
   * @param r Recipe to add
   * @return True if add was successful, else false
   */
  protected boolean addRecipe(Recipe r) {
    return RecipeFileHandler.write(r);
  }

  /**
   * Removes a recipe from the API.
   *
   * @param name Name of the Recipe to delete
   * @return True if remove is successful, else false
   */
  protected boolean removeRecipe(String name) {
    return RecipeFileHandler.deleteFile(name);
  }

  /**
   * Rename the Recipe.
   *
   * @param recipeName Old name of the Recipe
   * @param newName New name of the Recipe
   * @return True if name change is successful, else false
   */
  protected boolean renameRecipe(String recipeName, String newName) {
    Recipe r = RecipeFileHandler.readRecipe(recipeName);
    if (RecipeFileHandler.renameFile(recipeName, newName)) {
      r.setName(newName);
      return RecipeFileHandler.write(r);
    }
    return false;
  }

  /**
   * Add new ingredient.
   *
   * @param recipeName Name of the Recipe to add Ingredient to
   * @param i Ingredient to add
   * @return True if add is successful
   */
  protected boolean addIngredient(String recipeName, Ingredient i) {
    Recipe r = RecipeFileHandler.readRecipe(recipeName);
    r.addItem(i.getName(), i.getPerPersonAmount(), i.getRoundUpTo(), i.getMeasuringUnit());
    r.setBought(i.getName(), i.getBought());
    return RecipeFileHandler.write(r);
  }

  /**
   * Delete an ingredient.
   *
   * @param recipeName Name of the Recipe that contains the Ingredient
   * @param ingredientName Name of the ingredient that will be deleted
   * @return True if successful, else False
   */
  protected boolean deleteIngredient(String recipeName, String ingredientName) {
    Recipe r = RecipeFileHandler.readRecipe(recipeName);
    r.deleteItem(ingredientName);
    return RecipeFileHandler.write(r);
  }

  /**
   * Change the ingredient name.
   *
   * @param recipeName The name of the recipe to be changed
   * @param oldIngredientName the old name of the ingredient
   * @param newIngredientName the new name of the ingredient
   * @return True if successful, else False
   */
  protected boolean changeIngredientName(String recipeName, String oldIngredientName,
      String newIngredientName) {
    Recipe r = RecipeFileHandler.readRecipe(recipeName);
    r.changeIngredientName(oldIngredientName, newIngredientName);
    return RecipeFileHandler.write(r);
  }

  /**
   * Set the amount per person.
   *
   * @param recipeName the name of the recipe containing the ingredient
   * @param ingredientName the name of the ingredient to be changed
   * @param perPersonAmount the Double of the amount per person
   * @return True if successful, else False
   */
  protected boolean setPerPersonAmount(String recipeName, String ingredientName,
      Double perPersonAmount) {
    Recipe r = RecipeFileHandler.readRecipe(recipeName);
    r.setIngredientPerPersonAmount(ingredientName, perPersonAmount);
    return RecipeFileHandler.write(r);

  }

  /**
   * Set the round up amount.
   *
   * @param recipeName the recipe that contains the ingredient
   * @param ingredientName the name of the Ingredient to be changed
   * @param roundUpAmount Double to be that is to be rounded to
   * @return True if successful, else False
   */
  protected boolean setRoundUpAmount(String recipeName, String ingredientName,
      Double roundUpAmount) {
    Recipe r = RecipeFileHandler.readRecipe(recipeName);
    r.setRoundUpTo(ingredientName, roundUpAmount);
    return RecipeFileHandler.write(r);
  }

  /**
   * Set the measuring unit.
   *
   * @param measuringUnit String of the unit to be set
   * @param recipeName name of the Recipe that contains the ingredient
   * @param ingredientName name of the ingredient to be changed
   * @return True if successful, else False
   */
  protected boolean setUnit(String measuringUnit, String recipeName, String ingredientName) {
    Recipe r = RecipeFileHandler.readRecipe(recipeName);
    r.setIngredientMeasurement(ingredientName, measuringUnit);
    return RecipeFileHandler.write(r);
  }

  /**
   * Set if ingredient is bought or not.
   *
   * @param bought Boolean
   * @param recipeName name of the recipe containing the ingredient
   * @param ingredientName the ingredient to be changed
   * @return True if successful, else False
   */
  protected boolean setBought(boolean bought, String recipeName, String ingredientName) {
    Recipe r = RecipeFileHandler.readRecipe(recipeName);
    r.setBought(ingredientName, bought);
    return RecipeFileHandler.write(r);
  }

}
