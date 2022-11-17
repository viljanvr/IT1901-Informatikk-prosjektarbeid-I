package tacocalc.core;

import java.util.Collection;
import java.util.HashMap;

/**
 * Class to keep track of all recipes. Functions like an entry manager.
 */
public class RecipeBook {

  private HashMap<String, Recipe> recipes = new HashMap<>();

  private int hashPos = 0;



  /**
   * Varargs constructor for RecipeBook.
   *
   * @param recipes Recipe[] containing recipes to be added to the RecipeBookgit
   */
  public RecipeBook(Recipe... recipes) {
    for (Recipe recipe : recipes) {
      addRecipe(recipe);
    }
  }

  /**
   * Method to retrieve all recipes from the RecipeBook.
   *
   * @return Returns all recipes in the RecipeBook
   */
  public Collection<Recipe> getAllRecipes() {
    return recipes.values();
  }

  /**
   * Adds a recipe to the RecipeBook.
   *
   * @param recipe Recipe to be added
   */
  public String addRecipe(Recipe recipe) {
    hashPos++;
    recipes.put(Integer.toString(hashPos), recipe);
    return Integer.toString(hashPos);
  }

  /**
   * Removes a recipe from the RecipeBook.
   *
   * @param id String of Recipe to be removed (key)
   */
  public void removeRecipe(String id) {
    this.recipes.remove(id);
  }

  /**
   * Returns a recipe based on an ID.
   *
   * @param id The ID to the recipe
   *
   * @return Given recipe
   */
  public Recipe getRecipeById(String id) {
    return recipes.get(id);
  }


  /**
   * To string method.
   */
  @Override
  public String toString() {
    return recipes.toString();
  }

}
