package tacocalc.restiapi;

import java.util.Collection;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacocalc.core.Recipe;

/**
 * Controller for REST API.
 */

@RestController
@RequestMapping("/api/v1")
public class RecipecalcController {


  @Autowired
  private RecipecalcService recipecalcService;

  /**
   * Gets all recipes from the API.
   *
   * @return A list of all recipes in the API
   */
  @GetMapping(value = "/recipes")
  public Collection<Recipe> getAllRecipes() {
    return recipecalcService.getAllRecipes();
  }

  /**
   * Adds a recipe to the API.
   *
   * @param recipe Recipe to be added
   * @return The id of the given recipe
   */
  @PostMapping(value = "/recipes/add", produces = "application/json")
  public String addRecipe(Recipe recipe) {
    String id = recipecalcService.addRecipe(recipe);
    return "{\"id\":\"" + id + "\" }";
  }


  /**
   * Returns a given recipe based on an ID.
   *
   * @param id ID to the recipe
   * @return Recipe with the given ID
   */
  @GetMapping(value = "/recipes/{id}", produces = "application/json")
  public Recipe getRecipe(final @PathVariable("id") String id) {
    return recipecalcService.getRecipeById(id);
  }



  /**
   * Removes a Recipe from the API if it exists.
   *
   * @param id The id of the Recipe to remove
   */
  @DeleteMapping(value = "recipes/{id}")
  public void removeRecipe(final @PathVariable("id") String id) {
    try {
      recipecalcService.removeRecipe(id);
    } catch (NoSuchElementException e) {
      e.printStackTrace();
    }
  }
}
