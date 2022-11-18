package tacocalc.restapi;


import tacocalc.core.Recipe;
import tacocalc.data.RecipeFileHandler;
import tacocalc.core.Ingredient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;
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
public class RecipeCalcControllerTest {

  private OkHttpClient client = new OkHttpClient();
  private RecipeFileHandler recipeFileHandler = new RecipeFileHandler();
  private ObjectMapper mapper = new ObjectMapper();
  private List<Recipe> recipes = new ArrayList<>();
  private String host = "http://localhost:";
  private static final String API_URL = "/api/v1/";
  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final String APPLICATION_JSON = "application/json";
  private RecipecalcService recipecalcService;
  @LocalServerPort
  int port;

  @BeforeEach
  public void setup() {
    recipes.clear();
  }

  @AfterEach
  public void teardown() {
    System.out.println("Jup jeg kjører");
    RecipeFileHandler.deleteFile("coolName");
  }

  @Test
  public void testGetRecipe() {
    Ingredient i = new Ingredient("agurk", 2.0, "stk");
    Recipe r = new Recipe("amogus", i);
    recipes.add(r);
    RecipeFileHandler.write(r);
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
    RecipeFileHandler.write(r);
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
    Recipe r = new Recipe("oldDumbName");
    // So Recipe is deleted after testrun
    Recipe r2 = new Recipe("coolName");
    recipes.add(r);
    recipes.add(r2);
    RecipeFileHandler.write(r);
    Boolean output = false;
    try {
      // TODO: Fix test so file is deleted after, as it is now renamed and therefore not deleted
      Request request = new Request.Builder()
          .url(host + port + API_URL + "recipes/" + r.getName() + "/name?newName=coolName")
          .put(RequestBody.create("", mediaType)).build();
      response = client.newCall(request).execute();
      responseBody = response.body();
      responseBodyString = responseBody.string();
      System.out.println("Jeg kjørte" + responseBodyString);
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
    RecipeFileHandler.write(r);
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

}
