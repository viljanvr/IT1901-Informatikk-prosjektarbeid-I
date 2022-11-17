package tacocalc.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IngredientTest {
  // Tests for Ingredient class and its setters

  @Test
  public void testIngredientConstructor() {
    Ingredient I1 = new Ingredient("kjøtt", 200.0, 400.0, "stk");
    Assertions.assertEquals("kjøtt", I1.getName());
    Assertions.assertEquals(200, I1.getPerPersonAmount());
    Assertions.assertEquals(400, I1.getRoundUpTo());
    Assertions.assertEquals("stk", I1.getMeasuringUnit());
  }

  @Test
  public void testPerPersonIngredientAmount() {
    Ingredient I1 = new Ingredient("agurk", 3.0, "stk");
    Assertions.assertEquals(3, I1.getPerPersonAmount());
    I1.setPerPersonAmount(4.0);
    Assertions.assertEquals(4, I1.getPerPersonAmount());
  }

  @Test
  public void testIngredientsBought() {
    Ingredient I2 = new Ingredient("ost", 1.0, "stk");
    Assertions.assertFalse(I2.getBought());
    I2.setBought(true);
    Assertions.assertTrue(I2.getBought());
  }

  @Test
  public void testGetTotalAmount() {
    Ingredient I1 = new Ingredient("ost", 1.0, "stk");
    Assertions.assertEquals(4, I1.getTotalAmount(4));
    Ingredient I2 = new Ingredient("agurk", 0.20, 0.5, "stk");
    Assertions.assertEquals(1.5, I2.getTotalAmount(7));
  }

  @Test
  public void testThrows() {
    Ingredient I1 = new Ingredient("agurk", 5.0, "stk");
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      I1.setPerPersonAmount(-1.0);
    });
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("agurk", 4.0, -1.0, "stk");
    });
  }
}
