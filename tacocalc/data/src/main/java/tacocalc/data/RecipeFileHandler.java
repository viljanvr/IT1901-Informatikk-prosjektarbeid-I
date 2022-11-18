package tacocalc.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import tacocalc.core.Recipe;

/**
 * Class for reading and writing recipies to file.
 */
public class RecipeFileHandler {
  private static final String FILEPATH = "/recipecalc/";

  private static final String RECIPE = "recipes/";
  private static final String TEMPLATE = "templates/";
  private static final String TEST = "test/";

  private static boolean testMode = false;

  /**
   * Takes a filename and stores the object in that given Json file. If it does not exist then it
   * simply creates it.
   *
   * @param r the recipie to write
   */
  public static boolean write(Recipe r) {
    String folder = testMode ? TEST : RECIPE;
    try (FileWriter fw = new FileWriter(getFilePath(r.getName(), folder), StandardCharsets.UTF_8)) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      gson.toJson(r, fw);
      return true;
    } catch (FileNotFoundException e) {
      File parent = new File(System.getProperty("user.home") + FILEPATH + folder);
      if (!parent.isFile()) {
        if (parent.mkdirs()) {
          return write(r);
        }
      }
      return false;
    } catch (IOException e) {
      return false;
    }
  }



  /**
   * Reads the file given by parameter fileName. If file does not exist it throws an exception. The
   * contents of the file is stored and returned as a new object of type ShoppingList
   *
   * @param name the String that is the name of the file to be read from
   *
   * @return a ShoppingList created from the contents of the Json file
   */
  public static Recipe readRecipe(String name) {
    return read(name, (testMode ? TEST : RECIPE));
  }

  public static Recipe readTemplate(String name) {
    return read(name, TEMPLATE);
  }

  private static Recipe read(String name, String folder) {
    try (FileReader fr = new FileReader(getFilePath(name, folder), StandardCharsets.UTF_8)) {
      Gson gson = new Gson();

      // Make Ingredient list from Gson

      // Type listType = new TypeToken<List<Recipe>>() {}.getType();
      // return gson.fromJson(fr, listType);

      return gson.fromJson(fr, Recipe.class);
      // Return shopping list from ArrayList
    } catch (IOException e) {
      return new Recipe(name);
    }
  }

  /**
   * Allow you to rename a given file.
   *
   * @param oldName the file to be renamed
   * @param newName the new file name
   * @return returns true if renaming succeded
   */
  public static boolean renameFile(String oldName, String newName) {
    String folder = testMode ? TEST : RECIPE;
    File oldFile = new File(getFilePath(oldName, folder));
    File newFile = new File(getFilePath(newName, folder));

    if (newFile.exists()) {
      return false;
    }
    return oldFile.renameTo(newFile);
  }

  public static boolean deleteFile(String name) {
    File file = new File(getFilePath(name, (testMode ? TEST : RECIPE)));
    return file.delete();
  }

  private static String getFilePath(String name, String folder) {
    if (!validFileName(name)) {
      throw new IllegalArgumentException("\"" + name + "\" is not a valid file name");
    }
    return System.getProperty("user.home") + FILEPATH + folder + name + ".json";
  }

  /**
   * Takes in a string as an argument and checks if it's a valid file name.
   *
   * @param s String to be checked
   * @return Returns true if the string is a valid file name.
   */
  public static boolean validFileName(String s) {
    return s == null || Pattern.matches(
        "^([æøåÆØÅa-zA-Z0-9_-]|[æøåÆØÅa-zA-Z0-9_-][æøåÆØÅa-zA-Z0-9 _-]*[æøåÆØÅa-zA-Z0-9_-])$", s);
  }

  /**
   * Get a List of all recipies from file.
   *
   * @return Returns a list of Recipe objects
   */
  public static List<Recipe> getAllRecipies() {
    String folder = (testMode ? TEST : RECIPE);
    File dir = new File(System.getProperty("user.home") + FILEPATH + folder);
    System.out
        .println("Reading recipes from " + System.getProperty("user.home") + FILEPATH + folder);

    FilenameFilter filter = new FilenameFilter() {
      @Override
      public boolean accept(File f, String name) {
        return name.endsWith(".json");
      }
    };

    File[] filesList = dir.listFiles(filter);
    if (filesList == null) {
      return new ArrayList<>();
    } else {
      return Arrays.stream(filesList).map(f -> readRecipe(f.getName().split("\\.")[0]))
          .collect(Collectors.toList());
    }
  }

  public static void setTestMode(Boolean b) {
    testMode = b;
  }
}
