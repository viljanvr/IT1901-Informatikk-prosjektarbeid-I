package tacocalc.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Class to keep track of all recipes. Functions like an entry manager.
 */
public class RecipeBook {

  private Collection<Recipe> recipes = new ArrayList<>();

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
    return new ArrayList<>(recipes);
  }

  /**
   * Adds a recipe to the RecipeBook.
   *
   * @param recipe Recipe to be added
   * @return The name of the recipe
   */
  public String addRecipe(Recipe recipe) {
    recipes.add(recipe);
    return recipe.getName();
  }

  /**
   * Removes a Recipe from the RecipeBook.
   *
   * @param name Name of Recipe to be removed
   */
  public void removeRecipe(String name) {
    this.recipes =
        recipes.stream().filter(r -> !(name.equals(r.getName()))).collect(Collectors.toList());
  }

  /**
   * Returns a recipe based on a name.
   *
   * @param name The name of the Recipe
   *
   * @return Given recipe
   */
  public Recipe getRecipeByName(String name) {
    return recipes.stream().filter(r -> name.equals(r.getName())).findAny().orElse(null);
  }


  /**
   * To string method.
   */
  @Override
  public String toString() {
    return recipes.toString();
  }

}
