package tacocalc.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import tacocalc.core.*;

public class FilehandlingTest {
  @Test
  public void testWrite() throws FileNotFoundException {
    RecipeFileHandler.setTestMode(true);

    RecipeFileHandler th = new RecipeFileHandler();
    Recipe r1 = new Recipe("Write-test");
    r1.addItem("agurk", 4.0, "stk");
    r1.addItem("paprika", 6.0, "stk");
    th.write(r1);
    File f1 = new File(System.getProperty("user.home") + "/recipecalc/test/Write-test.json");
    Assertions.assertTrue(f1.isFile());
    // Check if you can write to existing file
    Assertions.assertTrue(f1.canWrite());

    Gson gson = new Gson();
    assertEquals(gson.toJson(r1), gson.toJson(gson.fromJson(new FileReader(f1), Recipe.class)));
  }

  // @Test
  public void testRead() {
    RecipeFileHandler.setTestMode(true);

    RecipeFileHandler th = new RecipeFileHandler();
    Recipe r1 = new Recipe("Read-test");
    r1.addItem("genus", 3.0, "mange");
    th.write(r1);
    // If file doesn't exist it returns a new Recipe
    Recipe r2 = th.readRecipe("Read-test");
    Assertions.assertEquals(r1.getIngredientPerPersonAmount("genus"),
        r2.getIngredientPerPersonAmount("genus"));
    // If file doesn't exist it returns a new Recipe
    Assertions.assertEquals(0, th.readRecipe("NonFileNameOnlyForTesting").getList().size());
  }
}
