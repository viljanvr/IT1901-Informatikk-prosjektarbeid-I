package tacocalc.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LogicTest {

  // Tests for Ingredient class and its setters
  @Test
  public void testIngredientConstructor() {
    Ingredient I1 = new Ingredient("kjøtt", 200);
    assertEquals("kjøtt", I1.getName());
    assertEquals(200, I1.getAmount());
  }

  @Test
  public void testIngredientsAmount() {
    Ingredient I1 = new Ingredient("agurk", 3);
    assertEquals(3, I1.getAmount());
    I1.setAmount(4);
    assertEquals(4, I1.getAmount());
  }

  @Test
  public void testToStringmethods() {
    Ingredient I1 = new Ingredient("tomat", 4);
    assertEquals("[ ]: 4x tomat", I1.toString());
    Recipe r = new Recipe(I1);
    r.setBought("tomat", true);
    assertEquals("[x]: 4x tomat\n", r.toString());
  }

  @Test
  public void testIngredientsBought() {
    Ingredient I2 = new Ingredient("ost", 1);
    assertFalse(I2.getBought());
    I2.setBought(true);
    assertTrue(I2.getBought());
  }

  // Tests for the Shoppinglist class
  @Test
  public void testMultipleingredients() {
    Ingredient I1 = new Ingredient("agurk", 3);
    Ingredient I2 = new Ingredient("ost", 1);
    Recipe r = new Recipe(I1, I2);
    r.setPeople(1);
    assertEquals(1, r.getIngredientAmount("ost"));
    assertEquals(3, r.getIngredientAmount("agurk"));
    r.setIngredientAmount("ost", 2);
    r.setIngredientAmount("agurk", 2);
    assertEquals(2, r.getIngredientAmount("ost"));
    assertEquals(2, r.getIngredientAmount("agurk"));
  }

  @Test
  public void testShoppingListBought() {
    Ingredient I1 = new Ingredient("agurk", 3);
    Recipe r = new Recipe(I1);
    r.setBought("agurk", true);
    assertTrue(r.getBought("agurk"));
  }

  @Test
  public void testAddAndDelete() {
    Recipe r = new Recipe();
    r.setPeople(1);
    r.addItem("ost", 1);
    r.setBought("ost", true);
    assertTrue(r.getBought("ost"));
    // Adding the same ingredient again
    r.addItem("ost", 3);
    assertFalse(r.getBought("ost"));
    assertEquals(3, r.getIngredientAmount("ost"));
    r.deleteItem("ost");
    assertThrows(IllegalStateException.class, () -> {
      r.deleteItem("ost");
    });
  }

  // Tests for the shopping class that checks if throws are done correctly
  @Test
  public void testThrows() {
    Ingredient I1 = new Ingredient("tomat", 3);
    Recipe r = new Recipe(I1);
    r.setPeople(1);
    assertThrows(IllegalArgumentException.class, () -> {
      I1.setAmount(-1);
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
      r.setIngredientAmount("ost", 2);
    });
    assertThrows(IllegalStateException.class, () -> {
      r.getIngredientAmount("ost");
    });
  }
}
