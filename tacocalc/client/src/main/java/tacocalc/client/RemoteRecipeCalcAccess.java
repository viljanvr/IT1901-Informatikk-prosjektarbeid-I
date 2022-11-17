package tacocalc.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;
import tacocalc.core.Ingredient;
import tacocalc.core.Recipe;



/**
 * Client class that sends HTTP requests to the REST API.
 */
public class RemoteRecipeCalcAccess implements RecipeCalcAccess {

  private final String url;

  private final int port;

  private static final String APPLICATION_JSON = "application/json";

  private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

  private static final String ACCEPT_HEADER = "Accept";

  private static final String CONTENT_TYPE_HEADER = "Content-Type";


  public RemoteRecipeCalcAccess(final String url, final int port) {
    this.url = url;
    this.port = port;
  }

  /**
   * Returns a URL to the endpoint in the REST API.
   *
   * @param endpoint Location in the API
   * @return URL to the endpoint
   */
  private URI getUri(String endpoint) {
    try {
      return new URI(this.url + ":" + this.port + endpoint);
    } catch (URISyntaxException e) {
      return null;
    }

  }

  @Override
  public void changeRecipeName(Recipe r, String name) {
    // TODO Auto-generated method stub

  }



  @Override
  public void setBought(Recipe recipe, String ingredientName, Boolean bought) {
    try {
      HttpRequest request = HttpRequest
          .newBuilder(getUri(
              "/api/v1/recipes/" + recipe.getName() + "/" + ingredientName + "?bought=" + bought))
          .header(ACCEPT_HEADER, APPLICATION_JSON).header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .PUT(BodyPublishers.ofString("")).build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      System.out.println(responseString);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteIngredient(Recipe recipe, String ingredientName) {
    // TODO Auto-generated method stub

  }



  @Override
  public void updateIngredient(Recipe recipe, String ingredient, String newIngredientName,
      Double perPersonAmount, Double roundUpTo, String measuringUnit) {
    // try {
    // HttpRequest request = HttpRequest.newBuilder(getUri("/api/v1/recipes/add"))
    // .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
    // .POST(BodyPublishers.ofString(new Gson().toJson(r))).build();
    // final HttpResponse<String> response =
    // HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
    // String responseString = response.body();
    // System.out.println(responseString);
    // } catch (IOException | InterruptedException e) {
    // throw new RuntimeException(e);
    // }
  }



  @Override
  public void addIngredient(Recipe recipe, Ingredient ingredient) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addRecipe(Recipe r) {
    try {
      HttpRequest request = HttpRequest.newBuilder(getUri("/api/v1/recipes/add"))
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .POST(BodyPublishers.ofString(new Gson().toJson(r))).build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      System.out.println(responseString);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends HTTP Request to get a list of all recipes in the API.a
   */
  @Override
  public List<Recipe> getAllRecipes() {
    System.out.println("getTodoList(String name) :" + getUri("/api/v1/recipes/").toString());
    HttpRequest request = HttpRequest.newBuilder(getUri("/api/v1/recipes/"))
        .header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();
    try {
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      System.out.println("Response: " + responseString);

      Gson gson = new Gson();
      return gson.fromJson(responseString, new TypeToken<List<Recipe>>() {}.getType());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Main method for testing.
   */
  public static void main(String[] args) {
    RemoteRecipeCalcAccess rrca = new RemoteRecipeCalcAccess("http://localhost", 8080);
    Recipe r = new Recipe("detteerentest", new Ingredient("Ost", 100.0, "g"));
    rrca.addRecipe(r);
    List<Recipe> list = rrca.getAllRecipes();
    System.out.println(list.toString());
    rrca.setBought(r, "Ost", true);
    list = rrca.getAllRecipes();
    System.out.println(list.toString());
  }
}
