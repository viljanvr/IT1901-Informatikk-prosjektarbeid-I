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
import tacocalc.core.ShoppingList;

public class TacoCalcFileHandler {
  public static final String FILEPATH = "../../../resources/";

  /**
   * Takes a filename and stores the object in that given Json file. If it does
   * not exist then it
   * simply creates it.
   *
   * @param fileName the String of the filename to be written to
   */
  public void write(ShoppingList sl, String fileName) {
    String fp = FILEPATH + fileName + ".json";
    System.out.println("write ran");
    try (FileWriter fw = new FileWriter(fp, StandardCharsets.UTF_8)) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(sl.getList(), fw);
    } catch (FileNotFoundException e) {
      if (new File(FILEPATH).mkdir()) {
        write(sl, fileName);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the file given by parameter fileName. If file does not exist it throws
   * an exception. The
   * contents of the file is stored and returned as a new object of type
   * ShoppingList
   *
   * @param fileName the String that is the name of the file to be read from
   *
   * @return a ShoppingList created from the contents of the Json file
   */
  public ShoppingList read(ShoppingList sl, String fileName) {
    String fp = FILEPATH + fileName + ".json";
    try (FileReader fr = new FileReader(fp, StandardCharsets.UTF_8)) {
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
