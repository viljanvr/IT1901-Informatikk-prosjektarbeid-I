package tacocalc.core;

import java.io.File;
import java.io.FileNotFoundException;
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
    private final static String FILEPATH = "../core/src/main/resources/";

    /** 
        Adds all ingdredients to the objects list

        @param ingredients the Ingredients to be added
    */
    public ShoppingList(Ingredient... ingredients) {
        list.addAll(Arrays.asList(ingredients));
    }
    
    public List<Ingredient> getList() {
        return new ArrayList<>(list);
    }

    /**
        Adds a new Ingredient to the ShoppingList
        If it is already an Ingredient with the same name, 
        the old Ingredient will be updated with the amount of the new,
        and its bought status is set to false
        
        @param name the string to be added
        @param amount the integer to be added
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
    
    public void setBought(String name, Boolean bought) {
        Ingredient ingredient = getIngredient(name);
        if (ingredient == null) {
            throw new IllegalStateException(
                    "The item doesn't exist in the list, and can therefore not be set to bought");
        }
        getIngredient(name).setBought(bought);
    }

    public boolean getBought(String name) {
        Ingredient ingredient = getIngredient(name);
        if (ingredient == null) {
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
        if (ingredient == null) {
            throw new IllegalStateException("The item doesn't exist in the list, and can therefore not set new amount");
        }
        getIngredient(name).setAmount(amount);
    }

    public int getIngredientAmount(String name) {
        Ingredient ingredient = getIngredient(name);
        if (ingredient == null) {
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

    /**  
        Takes a filename and stores the object in that given Json file.
        If it does not exist then it simply creates it.
        
        @param fileName the String of the filename to be written to
    */    
    public void write(String fileName) {
        String fp = FILEPATH + fileName + ".json";
        System.out.println("write ran");
        try (FileWriter fw = new FileWriter(fp)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(list, fw);
            // list.stream().forEach(i -> gson.toJson(i, fw));
            System.out.println("Filewriter wrote");
        } catch (FileNotFoundException e) {
            new File(FILEPATH).mkdir();
            write(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /** 
        Reads the file given by parameter fileName.
        If file does not exist it throws an exception.
        The contents of the file is stored and returned as a new object of type ShoppingList
        
        @param fileName the String that is the name of the file to be read from
        
        @return a ShoppingList created from the contents of the Json file
    */
    public ShoppingList read(String fileName) {
        String fp = FILEPATH + fileName + ".json";
        try (FileReader fr = new FileReader(fp)) {
            Gson gson = new Gson();

            // Make Ingredient list from Gson
            Type listType = new TypeToken<List<Ingredient>>() {
            }.getType();
            ArrayList<Ingredient> ingredients = gson.fromJson(fr, listType);

            // Return shopping list from ArrayList
            return new ShoppingList(ingredients.toArray(new Ingredient[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
