package tacocalc.restiapi;

import java.util.Collection;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tacocalc.core.Ingredient;
import tacocalc.core.Recipe;
import tacocalc.core.RecipeBook;
import tacocalc.data.TacoCalcFileHandler;

/**
 * Service handler for REST API.
 */

@Service
public class TacocalcService {

  private TacoCalcFileHandler fh = new TacoCalcFileHandler();

  private RecipeBook recipes = new RecipeBook(
      new Recipe[] {new Recipe(new Ingredient("Pølser", 2), new Ingredient("Pølsebrød", 2)),
          new Recipe(new Ingredient("Ringnes", 6))});

  public Collection<Recipe> getAllRecipes() {
    return recipes.getAllRecipes();
  }

  /**
   * Adds a recipe to the RecipeBook.
   *
   * @param recipe Recipe to be added
   * @return The id of the recipe in the RecipeBook
   */
  public String addRecipe(Recipe recipe, String filename) {
    String id = recipes.addRecipe(recipe);
    fh.write(recipe, filename);
    return id;
  }

  /**
   * Returns a given recipe based on an ID.
   *
   * @param id ID to the recipe
   * @return Recipe with the given ID
   */
  public Recipe getRecipeById(String id) {
    return recipes.getRecipeById(id);
  }

  /**
   * Removes a recipe from the RecipeBook if it exists.
   *
   * @param id String of Recipe to be removed (key)
   */
  public void removeRecipe(String id) {
    if (recipes.getRecipeById(id) == null) {
      throw new NoSuchElementException(HttpStatus.NOT_FOUND + "Recipe not found");
    }
    recipes.removeRecipe(id);
  }


  /**
   * Main method for testing.
   *
   * @param args Args to run main function
   */
  public static void main(String[] args) {
    TacocalcService ts = new TacocalcService();

    System.out.println(ts.recipes.toString());
    System.out.println("\n");
    System.out.println(ts.recipes.getRecipeById("1"));
  }

}
