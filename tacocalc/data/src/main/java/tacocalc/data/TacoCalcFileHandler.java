package tacocalc.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import tacocalc.core.Ingredient;
import tacocalc.core.Recipe;

/**
 * Class for reading and writing recipies to file.
 */
public class TacoCalcFileHandler {
  public static final String FILEPATH = "../data/src/main/resources/";

  /**
   * Takes a filename and stores the object in that given Json file. If it does not exist then it
   * simply creates it.
   *
   * @param fileName the String of the filename to be written to
   */
  public void write(Recipe r, String fileName) {
    String fp = FILEPATH + fileName + ".json";
    try (FileWriter fw = new FileWriter(fp, StandardCharsets.UTF_8)) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(r.getList(), fw);
    } catch (FileNotFoundException e) {
      if (new File(FILEPATH).mkdir()) {
        write(r, fileName);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the file given by parameter fileName. If file does not exist it throws an exception. The
   * contents of the file is stored and returned as a new object of type ShoppingList
   *
   * @param fileName the String that is the name of the file to be read from
   *
   * @return a ShoppingList created from the contents of the Json file
   */
  public Recipe read(String fileName) {
    String fp = FILEPATH + fileName + ".json";
    try (FileReader fr = new FileReader(fp, StandardCharsets.UTF_8)) {
      Gson gson = new Gson();

      // Make Ingredient list from Gson
      Type listType = new TypeToken<List<Ingredient>>() {}.getType();
      ArrayList<Ingredient> ingredients = gson.fromJson(fr, listType);

      // Return shopping list from ArrayList
      return new Recipe(ingredients.toArray(new Ingredient[0]));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new Recipe();
  }
}