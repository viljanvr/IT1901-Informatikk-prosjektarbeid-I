package recipecalc.restapi;

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
import recipecalc.core.Ingredient;
import recipecalc.core.Recipe;

/**
 * Controller for REST API.
 */

@RestController
@RequestMapping("/api/v1")
public class RecipecalcController {

  @Autowired
  private RecipecalcService recipecalcService;

  /**
   * Returns a list of all the recipes.
   *
   * @return A list with Recipe objects
   */
  @GetMapping(path = "/recipes", produces = "application/json")
  public List<Recipe> getAllRecipes() {
    return recipecalcService.getAllRecipes();
  }

  /**
   * Returns a list of all recipe templates.
   *
   * @return A list with Recipe objects
   */
  @GetMapping(path = "/templates", produces = "application/json")
  public List<Recipe> getAllTemplates() {
    return recipecalcService.getAllTemplates();
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
   * @return True if add was successful
   */
  @PostMapping(path = "/recipes/recipe", consumes = "application/json")
  public boolean addRecipe(@RequestBody Recipe r) {
    return recipecalcService.addRecipe(r);
  }

  /**
   * Removes a Recipe from the API if it exists.
   *
   * @param recipeName The name of the Recipe to remove
   * @return True if successful
   */
  @DeleteMapping(value = "/recipes/{recipe}")
  public boolean removeRecipe(final @PathVariable("recipe") String recipeName) {
    return recipecalcService.removeRecipe(recipeName);
  }

  /**
   * Rename an already existing recipe.
   *
   * @param recipeName Recipe to be renamed
   * @param newName New name of recipe
   * @return True if rename was successful
   */
  @PutMapping(path = "/recipes/{recipe}/name", consumes = "application/json")
  public boolean renameRecipe(@RequestParam(value = "newName", required = true) String newName,
      @PathVariable("recipe") String recipeName) {
    return recipecalcService.renameRecipe(recipeName, newName);
  }

  /**
   * Add ingredient to a recipe.
   *
   * @param recipeName Name of recipe in which the ingredient is added to
   * @param i Recipe to be added
   * @return True if add was successful
   */
  @PostMapping(path = "/recipes/{recipe}/ingredient", consumes = "application/json")
  public boolean addIngredient(@PathVariable("recipe") String recipeName,
      @RequestBody Ingredient i) {
    return recipecalcService.addIngredient(recipeName, i);
  }

  @DeleteMapping(path = "/recipes/{recipe}/{ingredient}")
  public boolean deleteIngredient(@PathVariable("recipe") String recipeName,
      @PathVariable("ingredient") String ingredientName) {
    return recipecalcService.deleteIngredient(recipeName, ingredientName);
  }

  /**
   * Changes the name of an ingredient.
   *
   * @param newIngredientName The new name of the ingredient
   * @param recipeName Name of the recipe that contains the ingredient
   * @param oldIngredientName The old name of the ingredient
   * @return True if the state was changed
   */
  @PutMapping(path = "/recipes/{recipe}/{ingredient}/name")
  public boolean changeIngredientName(
      @RequestParam(value = "newName", required = true) String newIngredientName,
      @PathVariable(value = "recipe") String recipeName,
      @PathVariable(value = "ingredient") String oldIngredientName) {
    return recipecalcService.changeIngredientName(recipeName, oldIngredientName, newIngredientName);
  }

  /**
   * Changes the amount per person of a given ingredient.
   *
   * @param perPersonAmount The amount per person to be set
   * @param recipeName Name of the recipe that contains the ingredient
   * @param oldIngredientName The old name of the ingredient
   * @return True if the state was changed
   */
  @PutMapping(path = "/recipes/{recipe}/{ingredient}/amount")
  public boolean setPerPersonAmount(
      @RequestParam(value = "amount", required = true) Double perPersonAmount,
      @PathVariable(value = "recipe") String recipeName,
      @PathVariable(value = "ingredient") String oldIngredientName) {
    return recipecalcService.setPerPersonAmount(recipeName, oldIngredientName, perPersonAmount);
  }

  /**
   * Set the round up amount for an ingredient.
   *
   * @param roundUpAmount The new round up amount to be set
   * @param recipeName Name of the recipe that contains the ingredient
   * @param oldIngredientName The old name of the ingredient
   * @return True if the state was changed
   */
  @PutMapping(path = "/recipes/{recipe}/{ingredient}/roundUp")
  public boolean setRoundUpAmount(
      @RequestParam(value = "roundUp", required = true) Double roundUpAmount,
      @PathVariable(value = "recipe") String recipeName,
      @PathVariable(value = "ingredient") String oldIngredientName) {
    return recipecalcService.setRoundUpAmount(recipeName, oldIngredientName, roundUpAmount);
  }

  /**
   * Set measuring unit of an ingredient.
   *
   * @param measuringUnit The measuring unit to be set
   * @param recipeName Name of the recipe that contains the ingredient
   * @param ingredientName Name of the ingredient
   * @return True if the state was changed
   */
  @PutMapping(path = "/recipes/{recipe}/{ingredient}/unit")
  public boolean setUnit(
      @RequestParam(value = "measuringUnit", required = true) String measuringUnit,
      @PathVariable("recipe") String recipeName,
      @PathVariable("ingredient") String ingredientName) {
    return recipecalcService.setUnit(measuringUnit, recipeName, ingredientName);
  }

  /**
   * Changes the bought state in an ingredient.
   *
   * @param bought The bought state to be set
   * @param recipeName Name of the recipe that contains the ingredient
   * @param ingredientName Name of the ingredient
   * @return True if the state was changed
   */
  @PutMapping(path = "/recipes/{recipe}/{ingredient}/bought")
  public boolean setBought(@RequestParam(value = "bought", required = true) boolean bought,
      @PathVariable("recipe") String recipeName,
      @PathVariable("ingredient") String ingredientName) {
    return recipecalcService.setBought(bought, recipeName, ingredientName);
  }
}
