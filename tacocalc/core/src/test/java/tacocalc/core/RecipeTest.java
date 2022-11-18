package tacocalc.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecipeTest {
  @Test
  public void testMultipleIngredients() {
    Ingredient I1 = new Ingredient("agurk", 3.0, "stk");
    Ingredient I2 = new Ingredient("ost", 1.0, "stk");
    Recipe r = new Recipe("testRecipe", I1, I2);
    r.setNumberOfPeople(1);
    Assertions.assertEquals(1, r.getIngredientPerPersonAmount("ost"));
    Assertions.assertEquals(3, r.getIngredientPerPersonAmount("agurk"));
    r.setIngredientPerPersonAmount("ost", 2.0);
    r.setIngredientPerPersonAmount("agurk", 2.0);
    Assertions.assertEquals(2, r.getIngredientPerPersonAmount("ost"));
    Assertions.assertEquals(2, r.getIngredientPerPersonAmount("agurk"));
  }

  @Test
  public void testShoppingListBought() {
    Ingredient I1 = new Ingredient("agurk", 3.0, "stk");
    Recipe r = new Recipe("testRecipe", I1);
    r.setBought("agurk", true);
    Assertions.assertTrue(r.getBought("agurk"));
  }

  @Test
  public void testAddAndDelete() {
    Recipe r = new Recipe("testRecipe");
    r.addItem("ost", 1.0, "kg");
    r.setBought("ost", true);
    Assertions.assertTrue(r.getBought("ost"));
    // Adding the same ingredient again
    r.addItem("ost", 3.0, "kg");
    Assertions.assertFalse(r.getBought("ost"));
    Assertions.assertEquals(3, r.getIngredientPerPersonAmount("ost"));
    r.deleteItem("ost");
    Assertions.assertNull(r.getIngredient("ost"));
    Assertions.assertThrows(IllegalStateException.class, () -> {
      r.deleteItem("ost");
    });
  }

  @Test

  @DisplayName("Test that changeName method changes name of ingredient")
  public void testChangeName() {
    Recipe r = new Recipe("testRecipe");
    r.addItem("ost", 0.5, "stk");
    Assertions.assertEquals("[ ]: 0.5 stk ost", r.getIngredient("ost").toString());
    r.changeIngredientName("ost", "avokado");
    // Avokado should now be in reipe, and ost not
    Assertions.assertEquals("[ ]: 0.5 stk avokado", r.getIngredient("avokado").toString());
    Assertions.assertNull(r.getIngredient("ost"));
  }

  // Tests for the recipe class that checks if throws are done correctly

  @Test
  public void testThrows() {
    Ingredient I1 = new Ingredient("tomat", 3.0, "stk");
    Recipe r = new Recipe("testRecipe", I1);
    Assertions.assertThrows(IllegalStateException.class, () -> {
      r.getBought("ost");
    });
    Assertions.assertThrows(IllegalStateException.class, () -> {
      r.deleteItem("ost");
    });
    Assertions.assertThrows(IllegalStateException.class, () -> {
      r.setBought("ost", true);
    });
    Assertions.assertThrows(IllegalStateException.class, () -> {
      r.setIngredientPerPersonAmount("ost", 2.0);
    });
    Assertions.assertThrows(IllegalStateException.class, () -> {
      r.getIngredientPerPersonAmount("ost");
    });
    Assertions.assertThrows(IllegalStateException.class, () -> {
      r.changeIngredientName("ost", "cheddar");
    });
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      r.setNumberOfPeople(0);
    });
    Assertions.assertThrows(IllegalStateException.class, () -> {
      r.getRoundUpTo("ost");
    });
    Assertions.assertThrows(IllegalStateException.class, () -> {
      r.getIngredientTotalAmount("ost");
    });
    Assertions.assertThrows(IllegalStateException.class, () -> {
      r.getMeasuringUnit("ost");
    });
    Assertions.assertThrows(IllegalStateException.class, () -> {
      r.setIngredientMeasurement("ost", "stk");
    });
  }
}
