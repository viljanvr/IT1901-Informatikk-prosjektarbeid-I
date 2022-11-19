package recipecalc.restapi;


import recipecalc.core.Recipe;
import recipecalc.data.RecipeFileHandler;
import recipecalc.core.Ingredient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {RecipecalcApplication.class, RecipecalcController.class, RecipecalcService.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipecalcApplicationTest {

  private OkHttpClient client = new OkHttpClient();
  // Needed to mock filehandler
  private RecipeFileHandler recipeFileHandler;
  private ObjectMapper mapper = new ObjectMapper();
  private List<Recipe> recipes = new ArrayList<>();
  private String host = "http://localhost:";
  private static final String API_URL = "/api/v1/";
  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final String APPLICATION_JSON = "application/json";
  private static final String ACCEPT_HEADER = "Accept";
  // Needed to mock service
  private RecipecalcService recipecalcService;

  @LocalServerPort
  int port;

  @BeforeEach
  public void setup() {
    recipes.clear();
  }

  @AfterEach
  public void teardown() {
    RecipeFileHandler.deleteFile("App-controller-test-file");
    // The Default files from getAllRecipes
    RecipeFileHandler.deleteFile("test");
    RecipeFileHandler.deleteFile("test2");

    RecipeFileHandler.deleteFile("testdeletei");
    RecipeFileHandler.deleteFile("TestRecipe");
    RecipeFileHandler.deleteFile("Ingredient-edit-controller-test-file");
    RecipeFileHandler.deleteFile("coolNewName");
    RecipeFileHandler.deleteFile("coolNameNy");
    RecipeFileHandler.deleteFile("addIngredient");
    RecipeFileHandler.deleteFile("coolName");
    RecipeFileHandler.deleteFile("testName");
    RecipeFileHandler.deleteFile("dumbOldName");
    RecipeFileHandler.deleteFile("amogus");
    RecipeFileHandler.deleteFile("sussy");
  }

  @Test
  public void testGetAllRecipes() {
    Ingredient i = new Ingredient("agurk", 2.0, "stk");
    Ingredient i2 = new Ingredient("tomat", 0.5, "stk");
    Recipe r = new Recipe("amogus", i);
    Recipe r2 = new Recipe("sussy", i2);
    RecipeFileHandler.writeRecipe(r);
    RecipeFileHandler.writeRecipe(r2);
    Request request = new Request.Builder().url(host + port + "/api/v1/recipes/")
        .header(ACCEPT_HEADER, APPLICATION_JSON).build();
    List<Recipe> returnList = new ArrayList<>();
    try {
      ResponseBody response = client.newCall(request).execute().body();
      CollectionLikeType listType =
          mapper.getTypeFactory().constructCollectionLikeType(List.class, Recipe.class);
      returnList = mapper.readValue(response.string(), listType);
    } catch (Exception e) {
      e.printStackTrace();
    }

    assertTrue(returnList.stream().anyMatch(recipe -> recipe.getName().equals(r.getName())));
    assertTrue(returnList.stream().anyMatch(recipe -> recipe.getName().equals(r2.getName())));

    assertEquals(r.getIngredientPerPersonAmount("agurk"),
        returnList.stream().filter(recipe -> recipe.getName().equals(r.getName())).findFirst().get()
            .getIngredientPerPersonAmount("agurk"));
    assertEquals(r2.getIngredientPerPersonAmount("tomat"),
        returnList.stream().filter(recipe -> recipe.getName().equals(r2.getName())).findFirst()
            .get().getIngredientPerPersonAmount("tomat"));
  }

  @Test
  public void testGetRecipe() {
    Ingredient i = new Ingredient("agurk", 2.0, "stk");
    Recipe r = new Recipe("amogus", i);
    recipes.add(r);
    RecipeFileHandler.writeRecipe(r);
    Request request = new Request.Builder().url(host + port + "/api/v1/recipes/" + r.getName())
        .header(CONTENT_TYPE_HEADER, APPLICATION_JSON).build();
    Recipe r2 = new Recipe();
    try {
      ResponseBody response = client.newCall(request).execute().body();
      r2 = mapper.readValue(response.string(), Recipe.class);
    } catch (Exception e) {
    }
    assertEquals(r.getName(), r2.getName());
  }

  @Test
  public void testPostAddRecipe() {
    Request request = null;
    Response response = null;
    ResponseBody responseBody = null;
    String responseBodyString = null;
    Ingredient i = new Ingredient("tomat", 1.0, "3");
    Recipe oldRecipe = new Recipe("test2", i);
    Boolean output = false;
    try {
      String sendString = mapper.writeValueAsString(oldRecipe);
      MediaType mediaType = MediaType.parse("application/json");
      request = new Request.Builder().url(host + port + API_URL + "recipes/recipe")
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .post(RequestBody.create(sendString, mediaType)).build();
      response = client.newCall(request).execute();
      responseBody = response.body();
      responseBodyString = responseBody.string();
      output = mapper.readValue(responseBodyString, Boolean.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Should return true if recipe was added
    assertTrue(output);
    assertTrue(response.isSuccessful());
  }

  @Test
  public void testDeleteRecipe() {
    ResponseBody responseBody = null;
    Response response = null;
    String responseBodyString = null;
    Recipe r = new Recipe("deleteMePls");
    recipes.add(r);
    RecipeFileHandler.writeRecipe(r);
    Boolean output = false;
    try {
      Request request = new Request.Builder().url(host + port + API_URL + "recipes/" + r.getName())
          .delete().build();
      response = client.newCall(request).execute();
      responseBody = response.body();
      responseBodyString = responseBody.string();
      output = mapper.readValue(responseBodyString, Boolean.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertTrue(output);
    assertTrue(response.isSuccessful());
  }

  @Test
  public void testPutRenameRecipe() {
    ResponseBody responseBody = null;
    Response response = null;
    String responseBodyString = null;
    MediaType mediaType = MediaType.parse("application/json");
    Recipe r = new Recipe("dumbOldName");
    recipes.add(r);
    RecipeFileHandler.writeRecipe(r);
    Boolean output = false;
    try {
      Request request = new Request.Builder()
          .url(host + port + API_URL + "recipes/" + r.getName() + "/name?newName=coolName")
          .put(RequestBody.create("", mediaType)).build();
      response = client.newCall(request).execute();
      responseBody = response.body();
      responseBodyString = responseBody.string();
      output = mapper.readValue(responseBodyString, Boolean.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertTrue(output);
    assertTrue(response.isSuccessful());
  }

  @Test
  public void testPostAddIngredient() {
    Request request = null;
    Response response = null;
    ResponseBody responseBody = null;
    String responseBodyString = null;
    Ingredient i = new Ingredient("tomat", 1.0, "stk");
    Ingredient newIngredient = new Ingredient("agurk", 3.0, "stk");
    Recipe r = new Recipe("addingredient", i);
    recipes.add(r);
    RecipeFileHandler.writeRecipe(r);
    Boolean output = false;
    try {
      String sendString = mapper.writeValueAsString(newIngredient);
      MediaType mediaType = MediaType.parse("application/json");
      request = new Request.Builder()
          .url(host + port + API_URL + "recipes/" + r.getName() + "/ingredient")
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .post(RequestBody.create(sendString, mediaType)).build();
      response = client.newCall(request).execute();
      responseBody = response.body();
      responseBodyString = responseBody.string();
      System.out.println(responseBodyString);
      output = mapper.readValue(responseBodyString, Boolean.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertTrue(output);
    assertTrue(response.isSuccessful());
  }

  @Test
  public void testDeleteIngredient() {
    ResponseBody responseBody = null;
    Response response = null;
    String responseBodyString = null;
    Ingredient i1 = new Ingredient("deleteMeBro", 20.0, "Ijustwannadie");
    Ingredient i2 = new Ingredient("dontDeleteMeBro", 20.0, "Ijustwannalive");

    Recipe r = new Recipe("testdeletei", i1, i2);
    recipes.add(r);
    RecipeFileHandler.writeRecipe(r);
    Boolean output = false;
    try {
      Request request = new Request.Builder()
          .url(host + port + API_URL + "recipes/" + r.getName() + "/" + i1.getName()).delete()
          .build();
      response = client.newCall(request).execute();
      responseBody = response.body();
      responseBodyString = responseBody.string();
      output = mapper.readValue(responseBodyString, Boolean.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Recipe result = RecipeFileHandler.readRecipe("testdeletei");
    assertEquals(1, result.getList().size());
    assertTrue(output);
    assertTrue(response.isSuccessful());
  }

  @Test
  public void changeIngredientName() {
    ResponseBody responseBody = null;
    Response response = null;
    String responseBodyString = null;
    MediaType mediaType = MediaType.parse("application/json");
    Ingredient i = new Ingredient("agurk", 0.5, "stk");
    Recipe r = new Recipe("testName", i);
    recipes.add(r);
    RecipeFileHandler.writeRecipe(r);
    Boolean output = false;
    try {
      Request request =
          new Request.Builder().url(host + port + API_URL + "recipes/" + r.getName() + "/"
              + i.getName() + "/name?newName=tomat").put(RequestBody.create("", mediaType)).build();
      response = client.newCall(request).execute();
      responseBody = response.body();
      responseBodyString = responseBody.string();
      output = mapper.readValue(responseBodyString, Boolean.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertTrue(output);
    assertTrue(response.isSuccessful());

    // Check that name actually has changed, but nothing else
    Recipe copyRecipe = RecipeFileHandler.readRecipe(r.getName());
    Ingredient check = new Ingredient("tomat", 0.5, "stk");
    Ingredient returnI = copyRecipe.getIngredient("tomat");
    assertEquals(check.getName(), returnI.getName());
    assertEquals(check.getPerPersonAmount(), returnI.getPerPersonAmount());
    assertEquals(check.getMeasuringUnit(), returnI.getMeasuringUnit());

  }

  @Test
  public void testSetPerPersonAmount() {
    ResponseBody responseBody = null;
    Response response = null;
    String responseBodyString = null;
    MediaType mediaType = MediaType.parse("application/json");
    Ingredient i = new Ingredient("agurk", 0.5, "stk");
    Recipe r = new Recipe("testName", i);
    recipes.add(r);
    RecipeFileHandler.writeRecipe(r);
    Boolean output = false;
    try {
      Request request = new Request.Builder().url(host + port + API_URL + "recipes/" + r.getName()
          + "/" + i.getName() + "/amount?amount=20").put(RequestBody.create("", mediaType)).build();
      response = client.newCall(request).execute();
      responseBody = response.body();
      responseBodyString = responseBody.string();
      output = mapper.readValue(responseBodyString, Boolean.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertTrue(output);
    assertTrue(response.isSuccessful());

    // Check that perpersonamount actually has changed, but nothing else
    Recipe copyRecipe = RecipeFileHandler.readRecipe(r.getName());
    Ingredient check = new Ingredient("agurk", 20.0, "stk");
    Ingredient returnI = copyRecipe.getIngredient("agurk");
    assertEquals(check.getName(), returnI.getName());
    assertEquals(check.getPerPersonAmount(), returnI.getPerPersonAmount());
    assertEquals(check.getMeasuringUnit(), returnI.getMeasuringUnit());
  }

  @Test
  public void testSetUnit() {
    ResponseBody responseBody = null;
    Response response = null;
    String responseBodyString = null;
    MediaType mediaType = MediaType.parse("application/json");
    Ingredient i = new Ingredient("agurk", 0.5, "stk");
    Recipe r = new Recipe("testName", i);
    recipes.add(r);
    RecipeFileHandler.writeRecipe(r);
    Boolean output = false;
    try {
      Request request = new Request.Builder().url(host + port + API_URL + "recipes/" + r.getName()
          + "/" + i.getName() + "/unit?measuringUnit=gram").put(RequestBody.create("", mediaType))
          .build();
      response = client.newCall(request).execute();
      responseBody = response.body();
      responseBodyString = responseBody.string();
      output = mapper.readValue(responseBodyString, Boolean.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertTrue(output);
    assertTrue(response.isSuccessful());

    // Check that measuringunit actually has changed, but nothing else
    Recipe copyRecipe = RecipeFileHandler.readRecipe(r.getName());
    Ingredient check = new Ingredient("agurk", 0.5, "gram");
    Ingredient returnI = copyRecipe.getIngredient("agurk");
    assertEquals(check.getName(), returnI.getName());
    assertEquals(check.getPerPersonAmount(), returnI.getPerPersonAmount());
    assertEquals(check.getMeasuringUnit(), returnI.getMeasuringUnit());
  }

  @Test
  public void setBought() {
    ResponseBody responseBody = null;
    Response response = null;
    String responseBodyString = null;
    MediaType mediaType = MediaType.parse("application/json");
    // Bought is false by default, so we first setBought = true
    Ingredient i = new Ingredient("agurk", 0.5, "stk");
    Recipe r = new Recipe("testName", i);
    r.setBought("agurk", true);
    recipes.add(r);
    RecipeFileHandler.writeRecipe(r);
    Boolean output = false;
    try {
      Request request = new Request.Builder().url(host + port + API_URL + "recipes/" + r.getName()
          + "/" + i.getName() + "/bought?bought=false").put(RequestBody.create("", mediaType))
          .build();
      response = client.newCall(request).execute();
      responseBody = response.body();
      responseBodyString = responseBody.string();
      output = mapper.readValue(responseBodyString, Boolean.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertTrue(output);
    assertTrue(response.isSuccessful());

    // Check that measuringunit actually has changed, but nothing else
    Recipe copyRecipe = RecipeFileHandler.readRecipe(r.getName());

    // Bought is false by default
    Ingredient check = new Ingredient("agurk", 0.5, "stk");
    Ingredient returnI = copyRecipe.getIngredient("agurk");
    assertEquals(check.getName(), returnI.getName());
    assertEquals(check.getBought(), returnI.getBought());
    assertEquals(check.getMeasuringUnit(), returnI.getMeasuringUnit());
  }

  @Test
  public void testSetRoundUpAmount() {
    ResponseBody responseBody = null;
    Response response = null;
    String responseBodyString = null;
    MediaType mediaType = MediaType.parse("application/json");
    // RoundUpAmount is 0.0 by default, so we set this first
    Ingredient i = new Ingredient("agurk", 0.5, "stk");
    Recipe r = new Recipe("testName", i);
    r.setRoundUpTo("agurk", 4.0);
    recipes.add(r);
    RecipeFileHandler.writeRecipe(r);
    Boolean output = false;
    try {
      Request request = new Request.Builder().url(host + port + API_URL + "recipes/" + r.getName()
          + "/" + i.getName() + "/roundUp?roundUp=0.0").put(RequestBody.create("", mediaType))
          .build();
      response = client.newCall(request).execute();
      responseBody = response.body();
      responseBodyString = responseBody.string();
      output = mapper.readValue(responseBodyString, Boolean.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertTrue(output);
    assertTrue(response.isSuccessful());

    // Check that measuringunit actually has changed, but nothing else
    Recipe copyRecipe = RecipeFileHandler.readRecipe(r.getName());

    // Bought is false by default
    Ingredient check = new Ingredient("agurk", 0.5, "stk");
    Ingredient returnI = copyRecipe.getIngredient("agurk");
    assertEquals(check.getName(), returnI.getName());
    assertEquals(check.getRoundUpTo(), returnI.getRoundUpTo());
    // Quick check that something else wasn't changed
    assertEquals(check.getMeasuringUnit(), returnI.getMeasuringUnit());
  }

}
