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
    TacoCalcFileHandler th = new TacoCalcFileHandler();
    Recipe r1 = new Recipe();
    r1.addItem("agurk", 4);
    r1.addItem("paprika", 6);
    th.write(r1, "testWrite");
    File f = new File("../data/src/main/resources/testWrite.json");
    Assertions.assertTrue(f.isFile());
    // Check if you can write to existing file
    Assertions.assertTrue(f.canWrite());
    // Check if new items are written over
    r1.addItem("banan", 18);
    th.write(r1, "testWrite2");
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
  public void testRead() {}
}
