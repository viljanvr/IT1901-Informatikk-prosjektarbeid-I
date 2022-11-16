package tacocalc.restiapi;

import org.springframework.stereotype.Service;

/**
 * Service handler for REST API.
 */

@Service
public class RecipecalcService {

  public RecipecalcService() {}

  // private RecipeFileHandler fh = new RecipeFileHandler();

  // private RecipeBook recipes = new RecipeBook(new Recipe[] {
  // new Recipe("Pølser", new Ingredient("Pølser", 2d, "stk"),
  // new Ingredient("Pølsebrød", 2d, "stk")),
  // new Recipe("Øl", new Ingredient("Ringnes", 6d, "stk"))});

  // public Collection<Recipe> getAllRecipes() {
  // return recipes.getAllRecipes();
  // }

  // /**
  // * Adds a recipe to the RecipeBook.
  // *
  // * @param recipe Recipe to be added
  // * @return The id of the recipe in the RecipeBook
  // */
  // public String addRecipe(Recipe recipe) {
  // String id = recipes.addRecipe(recipe);
  // fh.write(recipe);
  // return id;
  // }

  // /**
  // * Returns a given recipe based on an ID.
  // *
  // * @param id ID to the recipe
  // * @return Recipe with the given ID
  // */
  // public Recipe getRecipeById(String id) {
  // return recipes.getRecipeById(id);
  // }

  // /**
  // * Removes a recipe from the RecipeBook if it exists.
  // *
  // * @param id String of Recipe to be removed (key)
  // */
  // public void removeRecipe(String id) {
  // if (recipes.getRecipeById(id) == null) {
  // throw new NoSuchElementException(HttpStatus.NOT_FOUND + "Recipe not found");
  // }
  // recipes.removeRecipe(id);
  // }
}
