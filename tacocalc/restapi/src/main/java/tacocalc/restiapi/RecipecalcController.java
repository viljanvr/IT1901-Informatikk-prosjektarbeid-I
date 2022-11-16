package tacocalc.restiapi;

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
   * Adds a recipe to the API.
   *
   * @param recipe Recipe to be added
   * @return The name of the given recipe
   */
  @PostMapping(value = "/recipebook/{name}", produces = "application/json")
  public String addRecipe(Recipe recipe) {
    return "";
  }


  /**
   * Returns a given recipe based on an ID.
   *
   * @param name Name of the recipe
   * @return Recipe with the given ID
   */
  @GetMapping(value = "/recipebook/{name}", produces = "application/json")
  public Recipe getRecipe(final @PathVariable("name") String name) {
    return new Recipe("asdasd");
  }



  /**
   * Removes a Recipe from the API if it exists.
   *
   * @param name The name of the Recipe to remove
   */
  @DeleteMapping(value = "recipebook/{name}")
  public void removeRecipe(final @PathVariable("name") String name) {

  }

  // GET: recipies/
  // POST: recipies/name=""
  // DELETE: recipies/name=""
  // PUT: recipies/name=""
  // PUT: recipies/people=""

  // PUT: recipies/ingredient/name=""
  // PUT: recipies/ingredient/amount=""
  // PUT: recipies/ingredient/round=""
  // PUT: recipies/ingredient/bought=""
  // PUT: recipes/ingredient/unit=""
}
