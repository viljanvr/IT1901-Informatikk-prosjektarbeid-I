package tacocalc.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IngredientTest {
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

  @Test
  public void testThrows() {
    Ingredient I1 = new Ingredient("agurk", 5.0, "stk");
    assertThrows(IllegalArgumentException.class, () -> {
      I1.setPerPersonAmount(-1.0);
    });
  }
}
