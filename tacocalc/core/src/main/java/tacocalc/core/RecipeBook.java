package tacocalc.core;

import java.util.ArrayList;

public class RecipeBook {

  private String owner;
  private ArrayList<Recipe> recipes = new ArrayList<>();

  private RecipeBook(Recipe... recipes) {
    for (Recipe recipe : recipes) {
      this.recipes.add(recipe);
    }
  }

  public String getOwner() {
    return this.owner;
  }

  public ArrayList<Recipe> getRecipes() {
    return new ArrayList<>(recipes);
  }

  public Recipe getRecipe(String name) {
    return recipes.stream().filter(r -> r.getName().equals(name)).findAny().orElse(null);
  }

}
