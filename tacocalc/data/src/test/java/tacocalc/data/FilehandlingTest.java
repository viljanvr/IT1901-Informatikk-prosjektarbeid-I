package tacocalc.data;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tacocalc.core.*;

public class FilehandlingTest {
  @Test
  public void testWrite() {
    RecipeFileHandler.setTestMode(true);

    RecipeFileHandler th = new RecipeFileHandler();
    Recipe r1 = new Recipe("Write-test");
    r1.addItem("agurk", 4.0, "stk");
    r1.addItem("paprika", 6.0, "stk");
    th.write(r1);
    File f = new File(System.getProperty("user.home") + "/recipecalc/test/Write-test.json");
    Assertions.assertTrue(f.isFile());
    // Check if you can write to existing file
    Assertions.assertTrue(f.canWrite());

    // Check if new items are written over
    r1.addItem("banan", 18.0, "stk");
    r1.setName("testWrite2");
    th.write(r1);
    Path p1 = Paths.get("testWrite");
    Path p2 = Paths.get("testWrite2");
    try {
      Assertions.assertTrue(Files.mismatch(p1, p2) == -1);
    } catch (Exception e) {
      System.out.println("Files failed to load");
      e.printStackTrace();
    }
  }

  @Test
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
