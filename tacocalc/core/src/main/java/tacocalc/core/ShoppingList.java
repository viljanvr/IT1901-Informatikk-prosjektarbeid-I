package tacocalc.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingList {
  ArrayList<Ingredient> list = new ArrayList<>();

  /**
   * Adds all ingdredients to the objects list.
   *
   * @param ingredients the Ingredients to be added
   */
  public ShoppingList(Ingredient... ingredients) {
    list.addAll(Arrays.asList(ingredients));
  }

  public List<Ingredient> getList() {
    return new ArrayList<>(list);
  }

  /**
   * Adds a new Ingredient to the ShoppingList If it is already an Ingredient with the same name,
   * the old Ingredient will be updated with the amount of the new, and its bought status is set to
   * false.
   *
   * @param name the string to be added
   * @param amount the integer to be added
   */
  public void addItem(String name, Integer amount) {
    Ingredient duplicate = getIngredient(name);
    if (duplicate != null) {
      duplicate.setAmount(amount);
      duplicate.setBought(false);
    } else {
      list.add(new Ingredient(name, amount));
    }
  }

  public void setBought(String name, Boolean bought) {
    Ingredient ingredient = getIngredient(name);
    if (ingredient == null) {
      throw new IllegalStateException(
        "The item doesn't exist in the list, and can therefore not be set to bought"
      );
    }
    getIngredient(name).setBought(bought);
  }

  public boolean getBought(String name) {
    Ingredient ingredient = getIngredient(name);
    if (ingredient == null) {
      throw new IllegalStateException(
        "This ingredient is not in the shoppinglist"
      );
    }
    return getIngredient(name).getBought();
  }

  public void deleteItem(String name) {
    Ingredient ingredient = getIngredient(name);
    if (ingredient == null) {
      throw new IllegalStateException(
        "The item doesn't exist in the list, and can therefore not be removed"
      );
    }
    list.remove(getIngredient(name));
  }

  public Ingredient getIngredient(String name) {
    for (Ingredient ingredient : list) {
      if (ingredient.getName().equals(name)) {
        return ingredient;
      }
    }
    return null;
  }

  public void changeIngredientName(String originalName, String newName) {
    Ingredient ingredient = getIngredient(originalName);
    if (ingredient == null) {
      throw new IllegalStateException("The item doesn't exist in the list");
    }
    ingredient.setName(newName);
  }

  public void setIngredientAmount(String name, int amount) {
    Ingredient ingredient = getIngredient(name);
    if (ingredient == null) {
      throw new IllegalStateException(
        "The item doesn't exist in the list, and can therefore not set new amount"
      );
    }
    getIngredient(name).setAmount(amount);
  }

  public int getIngredientAmount(String name) {
    Ingredient ingredient = getIngredient(name);
    if (ingredient == null) {
      throw new IllegalStateException(
        "This ingredient is not in the shoppinglist"
      );
    }
    return getIngredient(name).getAmount();
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    String s = "";
    for (Ingredient ingredient : list) {
      s = sb.append(s).append(ingredient.toString()).append("\n").toString();
    }
    return s;
  }
}
