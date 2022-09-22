package tacocalc.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LogicTest {
    
    @Test
    public void testIngredientConstructor() {
        Ingredient I1 = new Ingredient("kjøtt", 200);
        assertEquals("kjøtt", I1.getName());
        assertEquals(200, I1.getAmount());
    }

    //Checks if the value of an ingredient is set correctly 
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
        ShoppingList SL = new ShoppingList(I1);
        SL.setBought("tomat", true);
        assertEquals("[x]: 4x tomat\n", SL.toString());
    }

    @Test
    public void testIngredientsBought() {
        Ingredient I2 = new Ingredient("ost", 1);
        assertFalse(I2.getBought());
        I2.setBought(true);
        assertTrue(I2.getBought());
    }

    @Test
    public void testMultipleingredients() {
        Ingredient I1 = new Ingredient("agurk", 3);
        Ingredient I2 = new Ingredient("ost", 1);
        ShoppingList SL = new ShoppingList(I1, I2);
        assertEquals(1, SL.getIngredientAmount("ost"));
        assertEquals(3, SL.getIngredientAmount("agurk"));
        SL.setIngredientAmount("ost", 2);
        SL.setIngredientAmount("agurk", 2);
        assertEquals(2, SL.getIngredientAmount("ost"));
        assertEquals(2, SL.getIngredientAmount("agurk"));
    }

    @Test
    public void testShoppingListBought() {
        Ingredient I1 = new Ingredient("agurk", 3);
        ShoppingList SL = new ShoppingList(I1);
        SL.setBought("agurk", true);
        assertTrue(SL.getBought("agurk"));
    }

    @Test
    public void testAddAndDelete() {
        ShoppingList SL = new ShoppingList();
        SL.addItem("ost", 1);
        SL.setBought("ost", true);
        assertTrue(SL.getBought("ost"));
        // Adding the same ingredient again
        SL.addItem("ost", 3);
        assertFalse(SL.getBought("ost"));
        assertEquals(3, SL.getIngredientAmount("ost"));
        SL.deleteItem("ost");
        assertThrows(IllegalStateException.class, () -> {
            SL.deleteItem("ost");
        }
        );
    }

    @Test
    public void testThrows() {
        Ingredient I1 = new Ingredient("tomat", 3);
        ShoppingList SL = new ShoppingList(I1);
        assertThrows(IllegalArgumentException.class, () -> {
            I1.setAmount(-1);
        }
        );
        assertThrows(IllegalStateException.class, () -> {
            SL.getBought("ost");
        }
        );
        assertThrows(IllegalStateException.class, () -> {
            SL.deleteItem("ost");
        }
        );
        assertThrows(IllegalStateException.class, () -> {
            SL.setBought("ost", true);
        }
        );
        assertThrows(IllegalStateException.class, () -> {
            SL.setIngredientAmount("ost", 2);
        }
        );
        assertThrows(IllegalStateException.class, () -> {
            SL.getIngredientAmount("ost");
        }
        );
    }
}
