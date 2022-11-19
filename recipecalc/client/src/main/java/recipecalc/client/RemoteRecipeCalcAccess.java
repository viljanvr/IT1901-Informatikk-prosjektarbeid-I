package recipecalc.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import recipecalc.core.Ingredient;
import recipecalc.core.Recipe;

/**
 * Client class that sends HTTP requests to the REST API.
 */
public class RemoteRecipeCalcAccess implements RecipeCalcAccess {

  private final String url;

  private final int port;

  private static final String API_URL = "/api/v1/";

  private static final String APPLICATION_JSON = "application/json";

  private static final String ACCEPT_HEADER = "Accept";

  private static final String CONTENT_TYPE_HEADER = "Content-Type";

  public RemoteRecipeCalcAccess(final String url, final int port) {
    this.url = url;
    this.port = port;
  }

  /**
   * Sends HTTP Request to get a list of all recipes in the API.a
   */
  @Override
  public List<Recipe> getAllRecipes() {
    return getListOfRecipeRequest(getUri("recipes/"));
  }

  @Override
  public List<Recipe> getAllTemplates() {
    return getListOfRecipeRequest(getUri("templates/"));
  }

  private List<Recipe> getListOfRecipeRequest(URI uri) {
    HttpRequest request =
        HttpRequest.newBuilder(uri).header(ACCEPT_HEADER, APPLICATION_JSON).GET().build();
    try {
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      sysOutHttpReq(request.method().toString(), uri.toString(),
          Boolean.toString(response.statusCode() == 200));
      return new Gson().fromJson(responseString, new TypeToken<List<Recipe>>() {}.getType());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends a HTTP Post request to add a new recipe.
   *
   * @param r The recipe to be added
   * @return True if successful, else false.
   */
  @Override
  public boolean addRecipe(Recipe r) {
    URI uri = getUri("recipes/recipe");
    try {
      HttpRequest request =
          HttpRequest.newBuilder(uri).header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
              .POST(BodyPublishers.ofString(new Gson().toJson(r))).build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      Boolean result = new Gson().fromJson(addWhitespace(response.body()), Boolean.class);
      sysOutHttpReq(request.method().toString(), uri.toString(), result.toString());
      return result;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Delete recipe.
   *
   * @param recipeName Recipe to be deleted.
   * @return True if successful, else false.
   */
  @Override
  public boolean deleteRecipe(String recipeName) {
    URI uri = getUri("recipes/" + recipeName);
    try {
      HttpRequest request = HttpRequest.newBuilder(uri)
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON).DELETE().build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      Boolean result = new Gson().fromJson(response.body(), Boolean.class);
      sysOutHttpReq(request.method().toString(), uri.toString(), result.toString());
      return result;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends a HTTP Put request to rename a Recipe.
   *
   * @param oldName Recipe to be renamed
   * @param newName New name of recipe
   * @return True if successful, else false.
   */
  @Override
  public boolean changeRecipeName(String oldName, String newName) {
    URI uri = getUri("recipes/" + oldName + "/name?newName=" + newName);
    try {
      HttpRequest request = HttpRequest.newBuilder(uri)
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON).PUT(BodyPublishers.ofString("")).build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      Boolean result = new Gson().fromJson(response.body(), Boolean.class);
      sysOutHttpReq(request.method().toString(), uri.toString(), result.toString());
      return result;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends a HTTP request to add an ingredient to a recipe.
   *
   * @param recipeName The name of the recipe to add the ingredient to
   * @param ingredient The ingredient to add
   * @return True if successful, else false.
   */
  @Override
  public boolean addIngredient(String recipeName, Ingredient ingredient) {
    URI uri = getUri("recipes/" + recipeName + "/ingredient");
    try {
      HttpRequest request =
          HttpRequest.newBuilder(uri).header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
              .POST(BodyPublishers.ofString(new Gson().toJson(ingredient))).build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      Boolean result = new Gson().fromJson(response.body(), Boolean.class);
      sysOutHttpReq(request.method().toString(), uri.toString(), result.toString());
      return result;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * Delete Ingredient.
   *
   * @param recipeName Recipe which contains the ingredient
   * @param ingredientName String of the ingredient to be deleted
   * @return True if successful, else false.
   */
  @Override
  public boolean deleteIngredient(String recipeName, String ingredientName) {
    URI uri = getUri("recipes/" + recipeName + "/" + ingredientName);
    try {
      HttpRequest request = HttpRequest.newBuilder(uri)
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON).DELETE().build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      Boolean result = new Gson().fromJson(response.body(), Boolean.class);
      sysOutHttpReq(request.method().toString(), uri.toString(), result.toString());
      return result;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Update the properties of a given ingredient. Delegates to other methods, that each sends their
   * own HTTP request for separate properties.
   *
   * @param recipeName The recipe in which the ingredient is located
   * @param ingredientName The ingredient to be updated
   * @param newIngredientName New name
   * @param perPersonAmount New amount per person
   * @param roundUpTo New round up to amount
   * @param measuringUnit New measuring unit
   * @return True if successful, else false.
   */
  @Override
  public HashMap<String, Boolean> updateIngredient(String recipeName, String ingredientName,
      String newIngredientName, Double perPersonAmount, Double roundUpTo, String measuringUnit) {
    HashMap<String, Boolean> results = new HashMap<>();
    results.put("setPerPersonAmount",
        setIngredientPerPersonAmount(recipeName, ingredientName, perPersonAmount));
    results.put("setRoundUpTo", setRoundUpTo(recipeName, ingredientName, roundUpTo));
    results.put("setMeasuringUnit", setMeasuringUnit(recipeName, ingredientName, measuringUnit));
    results.put("changeIngredientName",
        changeIngredientName(recipeName, ingredientName, newIngredientName));
    return results;
  }

  /**
   * Update the name of a given ingredient.
   *
   * @param recipeName Name of the recipe in which the ingredient is located
   * @param ingredientName The ingredient to be updated
   * @param newIngredientName The new value to be set
   * @return True if successful, else false.
   */
  private boolean changeIngredientName(String recipeName, String ingredientName,
      String newIngredientName) {
    URI uri = getUri(
        "recipes/" + recipeName + "/" + ingredientName + "/name?newName=" + newIngredientName);
    return ingredientPropertyPutRequest(uri);
  }

  /**
   * Update the per person amount property of a given ingredient.
   *
   * @param recipeName Name of the recipe in which the ingredient is located
   * @param ingredientName The ingredient to be updated
   * @param perPersonAmount The new value to be set
   * @return True if successful, else false.
   */
  private boolean setIngredientPerPersonAmount(String recipeName, String ingredientName,
      Double perPersonAmount) {
    URI uri = getUri(
        "recipes/" + recipeName + "/" + ingredientName + "/amount?amount=" + perPersonAmount);
    return ingredientPropertyPutRequest(uri);
  }

  /**
   * Update the round up amount property of a given ingredient.
   *
   * @param recipeName Name of the recipe
   * @param ingredientName The ingredient to be updated
   * @param roundUpTo The new value to be set
   * @return True if successful, else false.
   */
  private boolean setRoundUpTo(String recipeName, String ingredientName, Double roundUpTo) {
    URI uri =
        getUri("recipes/" + recipeName + "/" + ingredientName + "/roundUp?roundUp=" + roundUpTo);
    return ingredientPropertyPutRequest(uri);
  }

  /**
   * Update the measuring unit property of a given ingredient.
   *
   * @param recipeName Name of the recipe
   * @param ingredientName The ingredient to be updated
   * @param measuringUnit The new value to be set
   * @return True if successful, else false.
   */
  private boolean setMeasuringUnit(String recipeName, String ingredientName, String measuringUnit) {
    URI uri = getUri(
        "recipes/" + recipeName + "/" + ingredientName + "/unit?measuringUnit=" + measuringUnit);
    return ingredientPropertyPutRequest(uri);
  }

  /**
   * Update the bought property of a given ingredient.
   *
   * @param recipeName The name of the recipe in which the ingredient is located
   * @param ingredientName The ingredient to be updated
   * @param bought The new value to be set
   * @return True if successful, else false.
   */
  @Override
  public boolean setBought(String recipeName, String ingredientName, Boolean bought) {
    URI uri = getUri("recipes/" + recipeName + "/" + ingredientName + "/bought?bought=" + bought);
    return ingredientPropertyPutRequest(uri);
  }

  /**
   * Sends HTTP Put request to update ingredient properties.
   *
   * @param uri The uri with the properties to be updated
   * @return True if successful, else false.
   */
  private boolean ingredientPropertyPutRequest(URI uri) {
    try {
      HttpRequest request = HttpRequest.newBuilder(uri)
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON).PUT(BodyPublishers.ofString("")).build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      Boolean result = new Gson().fromJson(response.body(), Boolean.class);
      sysOutHttpReq(request.method().toString(), uri.toString(), result.toString());
      return result;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void sysOutHttpReq(String reqType, String uri, String result) {
    System.out.println(reqType + "-request " + uri + " result was \"" + result + "\"");
  }

  /**
   * Returns a URL to the endpoint of the REST API.
   *
   * @param endpoint Location of the server
   * @return URL to the endpoint
   */
  private URI getUri(String endpoint) {
    try {
      return new URI(removeWhitespace(this.url + ":" + this.port + API_URL + endpoint));
    } catch (URISyntaxException e) {
      return null;
    }
  }

  private static String removeWhitespace(String s) {
    return s.replace("\s", "%20");
  }

  private static String addWhitespace(String s) {
    return s.replace("%20", " ");
  }
}
