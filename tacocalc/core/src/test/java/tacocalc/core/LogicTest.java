package tacocalc.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LogicTest {

  // Tests for Ingredient class and its setters

  @Test
  public void testIngredientConstructor() {
    Ingredient I1 = new Ingredient("kjøtt", 200.0, 400.0, "stk");
    assertEquals("kjøtt", I1.getName());
    assertEquals(200, I1.getPerPersonAmount());
    assertEquals(400, I1.getRoundUpTo());
    assertEquals("stk", I1.getMeasuringUnit());
  }

  @Test
  public void testPerPersonIngredientAmount() {
    Ingredient I1 = new Ingredient("agurk", 3.0, "stk");
    assertEquals(3, I1.getPerPersonAmount());
    I1.setPerPersonAmount(4.0);
    assertEquals(4, I1.getPerPersonAmount());
  }

  @Test
  public void testToStringmethods() {
    Ingredient I1 = new Ingredient("tomat", 4.0, "stk");
    assertEquals("[ ]: 4 stk tomat", I1.toString());
    Recipe r = new Recipe(I1);
    r.setBought("tomat", true);
    assertEquals("[x]: 4 stk tomat\n", r.toString());
  }

  @Test
  public void testIngredientsBought() {
    Ingredient I2 = new Ingredient("ost", 1.0, "stk");
    assertFalse(I2.getBought());
    I2.setBought(true);
    assertTrue(I2.getBought());
  }

  // Tests for the Shoppinglist class

  @Test
  public void testMultipleIngredients() {
    Ingredient I1 = new Ingredient("agurk", 3.0, "stk");
    Ingredient I2 = new Ingredient("ost", 1.0, "stk");
    Recipe r = new Recipe(I1, I2);
    r.setNumberOfPeople(1);
    assertEquals(1, r.getIngredientPerPersonAmount("ost"));
    assertEquals(3, r.getIngredientPerPersonAmount("agurk"));
    r.setIngredientPerPersonAmount("ost", 2.0);
    r.setIngredientPerPersonAmount("agurk", 2.0);
    assertEquals(2, r.getIngredientPerPersonAmount("ost"));
    assertEquals(2, r.getIngredientPerPersonAmount("agurk"));
  }

  @Test
  public void testShoppingListBought() {
    Ingredient I1 = new Ingredient("agurk", 3.0, "stk");
    Recipe r = new Recipe(I1);
    r.setBought("agurk", true);
    assertTrue(r.getBought("agurk"));
  }

  @Test
  public void testAddAndDelete() {
    Recipe r = new Recipe();
    r.addItem("ost", 1.0, "kg");
    r.setBought("ost", true);
    assertTrue(r.getBought("ost"));
    // Adding the same ingredient again
    r.addItem("ost", 3.0, "kg");
    assertFalse(r.getBought("ost"));
    assertEquals(3, r.getIngredientPerPersonAmount("ost"));
    r.deleteItem("ost");
    assertNull(r.getIngredient("ost"));
    assertThrows(IllegalStateException.class, () -> {
      r.deleteItem("ost");
    });
  }

  @Test

  @DisplayName("Test that changeName method changes name of ingredient")
  public void testChangeName() {
    Recipe r = new Recipe();
    r.addItem("ost", 0.5, "stk");
    assertEquals("[ ]: 0.5 stk ost", r.getIngredient("ost").toString());
    r.changeIngredientName("ost", "avokado");
    // Avokado should now be in reipe, and ost not
    assertEquals("[ ]: 0.5 stk avokado", r.getIngredient("avokado").toString());
    assertNull(r.getIngredient("ost"));
  }

  // Tests for the shopping class that checks if throws are done correctly

  @Test
  public void testThrows() {
    Ingredient I1 = new Ingredient("tomat", 3.0, "stk");
    Recipe r = new Recipe(I1);
    assertThrows(IllegalArgumentException.class, () -> {
      I1.setPerPersonAmount(-1.0);
    });
    assertThrows(IllegalStateException.class, () -> {
      r.getBought("ost");
    });
    assertThrows(IllegalStateException.class, () -> {
      r.deleteItem("ost");
    });
    assertThrows(IllegalStateException.class, () -> {
      r.setBought("ost", true);
    });
    assertThrows(IllegalStateException.class, () -> {
      r.setIngredientPerPersonAmount("ost", 2.0);
    });
    assertThrows(IllegalStateException.class, () -> {
      r.getIngredientPerPersonAmount("ost");
    });
  }
}
