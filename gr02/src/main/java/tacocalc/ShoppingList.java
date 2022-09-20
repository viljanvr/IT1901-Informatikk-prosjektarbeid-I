package tacocalc;

import java.util.ArrayList;
import java.util.Arrays;

public class ShoppingList {
    ArrayList<Ingredient> list = new ArrayList<>();

    public ShoppingList(Ingredient... ingredients) {
        list.addAll(Arrays.asList(ingredients));
    }

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
        if(ingredient == null) {
            throw new IllegalStateException("The item doesn't exist in the list, and can therefore not be set to bought");
        }
        getIngredient(name).setBought(bought);
    }

    public boolean getBought(String name) {
        Ingredient ingredient = getIngredient(name);
        if(ingredient == null) {
            throw new IllegalStateException("This ingredient is not in the shoppinglist");
        }
        return getIngredient(name).getBought();
    }

    public void deleteItem(String name) {
        Ingredient ingredient = getIngredient(name);
        if (ingredient == null) {
            throw new IllegalStateException("The item doesn't exist in the list, and can therefore not be removed");
        }
        list.remove(getIngredient(name));
    }

    private Ingredient getIngredient(String name) {
        for (Ingredient ingredient : list) {
            if (ingredient.getName().equals(name)) {
                return ingredient;
            }
        }
        return null;
    }

    public void setIngredientAmount(String name, int amount) {
        Ingredient ingredient = getIngredient(name);
        if(ingredient == null) {
            throw new IllegalStateException("The item doesn't exist in the list, and can therefore not set new amount");
        }
        getIngredient(name).setAmount(amount);
    }

    public int getingredientAmount(String name) {
        Ingredient ingredient = getIngredient(name);
        if(ingredient == null) {
            throw new IllegalStateException("This ingredient is not in the shoppinglist");
        }
        return getIngredient(name).getAmount();
    }

    public String toString() {
        String s = "";
        for (Ingredient ingredient : list) {
            s += ingredient.toString() + "\n";
        }
        return s;
    }

    public static void main(String[] args) {
        ShoppingList SL = new ShoppingList();
        SL.addItem("Avocado", 2);
        SL.addItem("Lefse", 4);
        System.out.println(SL.toString());

        SL.setBought("Lefse", true);
        System.out.println(SL.toString());

        SL.deleteItem("Avocado");
        System.out.println(SL.toString());

        SL.addItem("Lefse", 5);
        System.out.println(SL.toString());

    }

}
