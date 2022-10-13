package tacocalc.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for a single recipie, containing multiple ingredients.
 */
public class Recipe {
  ArrayList<Ingredient> list = new ArrayList<>();
  private String name;
  private int people;

  /**
   * Adds all ingdredients to the objects list.
   *
   * @param ingredients the Ingredients to be added
   */
  public Recipe(Ingredient... ingredients) {
    list.addAll(Arrays.asList(ingredients));
    this.people = 1;

  }

  /**
   * Give this recipie a new name.
   *
   * @param name the new name to be set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get all ingridients in this recipie.
   *
   * @return a list of Recipie objects
   */
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

  /**
   * Update the bought propeperty of given ingredient.
   *
   * @param name name of the ingredient to update
   * @param bought the new value to be set
   */
  public void setBought(String name, Boolean bought) {
    Ingredient ingredient = getIngredient(name);
    if (ingredient == null) {
      throw new IllegalStateException(
          "The item doesn't exist in the list, and can therefore not be set to bought");
    }
    getIngredient(name).setBought(bought);
  }

  /**
   * Get the bought value of a given ingredient.
   *
   * @param name the ingredient to get the value from
   * @return a boolean
   */
  public boolean getBought(String name) {
    Ingredient ingredient = getIngredient(name);
    if (ingredient == null) {
      throw new IllegalStateException("This ingredient is not in the shoppinglist");
    }
    return getIngredient(name).getBought();
  }

  /**
   * Delete an ingredient from this recipie. Will throw an IllegalStateException if the ingredient
   * is not present.
   *
   * @param name the name of the ingredient to be deleted.
   */
  public void deleteItem(String name) {
    Ingredient ingredient = getIngredient(name);
    if (ingredient == null) {
      throw new IllegalStateException(
          "The item doesn't exist in the list, and can therefore not be removed");
    }
    list.remove(getIngredient(name));
  }

  /**
   * Get the ingredient object for the given ingredient name.
   *
   * @param name the name of the ingredient to get
   * @return an Ingredient object
   */
  public Ingredient getIngredient(String name) {
    for (Ingredient ingredient : list) {
      if (ingredient.getName().equals(name)) {
        return ingredient;
      }
    }
    return null;
  }

  /**
   * Set the amount per person of a given ingredient.
   *
   * @param name The ingredient to be set
   * @param amountPerPerson The updated amount per person
   */
  public void setIngredientAmount(String name, Integer amountPerPerson) {
    Ingredient ingredient = getIngredient(name);
    if (ingredient == null) {
      throw new IllegalStateException(
          "The item doesn't exist in the list, and can therefore not set new amount");
    }
    getIngredient(name).setAmount(amountPerPerson);
  }

  /**
   * Get the amount per person of a given ingredient.
   *
   * @param name the ingredient to get the value from
   * @return the amount per person as an interger
   */
  public int getIngredientAmount(String name) {
    Ingredient ingredient = getIngredient(name);
    if (ingredient == null) {
      throw new IllegalStateException("This ingredient is not in the shoppinglist");
    }
    return (int) getIngredient(name).getAmount() * people;
  }

  /**
   * Get the name of this recipie.
   *
   * @return the name as a string
   */
  public String getName() {
    return name;
  }

  /**
   * Change the name of a given ingredient.
   *
   * @param originalName The ingredient which the name is to be changed
   * @param newName The new name to be set
   */
  public void changeIngredientName(String originalName, String newName) {
    Ingredient ingredient = getIngredient(originalName);
    if (ingredient == null) {
      throw new IllegalStateException("The item doesn't exist in the list");
    }
    ingredient.setName(newName);
  }

  /**
   * toString-method.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String s = "";
    for (Ingredient ingredient : list) {
      s = sb.append(s).append(ingredient.toString()).append("\n").toString();
    }
    return s;
  }

  /**
   * Get number of people owning this recipie.
   *
   * @return the number of people as an integer
   */
  public int getPeople() {
    return people;
  }

  /**
   * Set number of people owning this recipie.
   *
   * @param people the number of people to be set
   */
  public void setPeople(int people) {
    if (people < 1) {
      throw new IllegalArgumentException("A recipe must be for 1 or more people");
    }
    this.people = people;
  }
}