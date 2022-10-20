package tacocalc.core;

import java.lang.Math;

/**
 * Klassen brukes for å skalere ingredienser i recipes.
 */
public class Scalar {
  /**
   * This function takes in a recipe and scales it according to the per person
   * previous amount. If the amount is less than 1 after scaling it sets the
   * amount
   * of that Ingredient to 1, as it has not been removed.
   *
   * @param recipe The Recipe which is to be scaled
   *
   * @param s      The scaling integer
   *
   * @return The same Recipe but scaled
   */
  public Recipe scaleRecipe(Recipe recipe, int s) {
    int sprev = recipe.getPeople();
    for (Ingredient i : recipe.getList()) {
      if (i.getAmount() == 0) {
        continue;
      }
      double perPerson = (i.getAmount()).doubleValue() / ((Integer) sprev).doubleValue();
      if (i.getPerPerson() != 0) {
        perPerson = i.getPerPerson();
      }
      double scaled = perPerson * s;
      if (scaled >= 1) {
        // TODO: Find better solution for scaling when their scaling falls below zero
        i.setPerPerson(perPerson);
        i.setAmount(((Long) Math.round(scaled)).intValue());
      } else {
        i.setAmount(1);
      }
    }
    return recipe;
  }
}
