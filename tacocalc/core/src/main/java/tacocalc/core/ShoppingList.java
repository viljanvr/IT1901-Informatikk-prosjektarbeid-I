package tacocalc.core;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ShoppingList {
    ArrayList<Ingredient> list = new ArrayList<>();
    private final static String FILEPATH = "./tacocalc/core/src/main/resources/";

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

    public int getIngredientAmount(String name) {
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


    public void write(String fileName) {
        String fp = FILEPATH + fileName + ".json";
        try (FileWriter fw = new FileWriter(fp)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(list, fw);
            // list.stream().forEach(i -> gson.toJson(i, fw));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ShoppingList read(String fileName) {
        String fp = FILEPATH + fileName + ".json";
        try (FileReader fr = new FileReader(fp)){
            Gson gson = new Gson();
            
            // Make Ingredient list from Gson
            Type listType = new TypeToken<List<Ingredient>>() {}.getType(); 
            ArrayList<Ingredient> ingredients = gson.fromJson(fr, listType);
            
            // Return shopping list from ArrayList
            return new ShoppingList(ingredients.toArray(new Ingredient[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

}
