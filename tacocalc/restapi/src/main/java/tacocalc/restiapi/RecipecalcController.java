package tacocalc.restiapi;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tacocalc.core.Ingredient;
import tacocalc.core.Recipe;

/**
 * Controller for REST API.
 */

@RestController
@RequestMapping("/api/v1")
public class RecipecalcController {


  @Autowired
  private RecipecalcService recipecalcService;


  @GetMapping(path = "/recipes", produces = "application/json")
  public List<Recipe> getAllRecipes() {
    return recipecalcService.getAllRecipes();
  }

  /**
   * Returns a given recipe based on an ID.
   *
   * @param name Name of the recipe
   * @return Recipe with the given name
   */
  @GetMapping(path = "/recipes/{name}", produces = "application/json")
  public Recipe getRecipe(final @PathVariable("name") String name) {
    return recipecalcService.getRecipe(name);
  }

  /**
   * Adds a recipe to the API.
   *
   * @param r Recipe to be added
   * @return True if was successful. False if not.
   */
  @PostMapping(path = "/recipes/add", consumes = "application/json")
  public boolean addRecipe(@RequestBody Recipe r) {
    return recipecalcService.addRecipe(r);
  }



  @PutMapping(path = "/recipes/{recipe}/{ingredient}")
  public boolean setBought(@RequestParam(value = "bought") boolean bought,
      @PathVariable("recipe") String recipeName,
      @PathVariable("ingredient") String ingredientName) {
    return recipecalcService.setBought(bought, recipeName, ingredientName);
  }

  @PutMapping(path = "/recipes/{recipe}/{ingredient}")
  public void setUnit(@RequestParam(value = "measuringtUnit") String measuringUnit,
      @PathVariable("recipe") String recipeName,
      @PathVariable("ingredient") String ingredientName) {
    recipecalcService.setUnit(measuringUnit, recipeName, ingredientName);
  }


  /**
   * Removes a Recipe from the API if it exists.
   *
   * @param name The name of the Recipe to remove
   */
  @DeleteMapping(value = "recipebook/{name}/delete")
  public boolean removeRecipe(final @PathVariable("name") String name) {
    return recipecalcService.removeRecipe(name);
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
