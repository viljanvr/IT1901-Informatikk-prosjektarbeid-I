package tacocalc.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import tacocalc.core.Recipe;



/**
 * Client class that sends HTTP requests to the REST API.
 */
public class TacocalcClient {

  private final String url;

  private final int port;

  public TacocalcClient(final String url, final int port) {
    this.url = url;
    this.port = port;
  }


  /**
   * Gets all the recipes stored in the REST API.
   *
   * @return A map of all the recipes with id's as keys
   * @throws HttpServerErrorException If the request isn't good
   * @throws InterruptedException If the thread is interrupted in the process
   * @throws ExecutionException If the process did not execute properly
   * @throws URISyntaxException If the URI syntax is incorrect
   */
  public HashMap<String, Recipe> getRecipes() throws HttpServerErrorException, InterruptedException,
      ExecutionException, URISyntaxException {
    HttpResponse<String> response = this.get(getUrl("/api/v1/recipes"));
    return getMapFromHttpResponse(response);
  }

  /**
   * Returns a recipe based on an ID.
   *
   * @param id The ID of the recipe to get
   * @return Returns a recipe
   * @throws HttpServerErrorException If the request isn't good
   * @throws InterruptedException If the thread is interrupted in the process
   * @throws ExecutionException If the process did not execute properly
   * @throws URISyntaxException If the URI syntax is incorrect
   */
  public Recipe getRecipe(final String id) throws HttpServerErrorException, InterruptedException,
      ExecutionException, URISyntaxException {
    HttpResponse<String> response = this.get(getUrl("/api/v1/recipes" + id));
    return (Recipe) getMapFromHttpResponse(response).get(id);
  }

  /**
   * Sends a request to add a Recipe to the API.
   *
   * @param recipe A map representing
   * @return The ID to the recipe
   * @throws HttpServerErrorException If the request isn't good
   * @throws InterruptedException If the thread is interrupted in the process
   * @throws ExecutionException If the process did not execute properly
   * @throws URISyntaxException If the URI syntax is incorrect
   */
  public String addRecipe(HashMap<String, Recipe> recipe) throws HttpServerErrorException,
      InterruptedException, ExecutionException, URISyntaxException {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String payload = gson.toJson(recipe);
    HttpResponse<String> response = this.post(getUrl("/api/v1/recipes/add"), payload);
    return gson.fromJson(response.body(), JsonObject.class).get("id").getAsString();
  }

  /**
   * Deletes the recipe with the given ID.
   *
   * @param id The ID of the Recipe to be deleted
   * @throws InterruptedException If the thread is interrupted in the process
   * @throws ExecutionException If the process did not execute properly
   * @throws URISyntaxException If the URI syntax is incorrect
   */
  public void deleteRecipe(String id)
      throws InterruptedException, ExecutionException, URISyntaxException {
    this.delete("/api/v1/recipes/" + id);
  }

  /**
   * Makes a map from a HTTP response.
   *
   * @param response HTTP response to covert to map
   * @return HashMap consisting of the Recipes ID and Recipe object
   */
  private HashMap<String, Recipe> getMapFromHttpResponse(final HttpResponse<String> response) {
    Gson gson = new Gson();
    Type mapType = new TypeToken<HashMap<String, Recipe>>() {}.getType();
    return gson.fromJson(response.body(), mapType);

  }

  /**
   * Sends a HTTP GET request.
   *
   * @param endpoint The endpoint to fetch data from
   * @return A HTTP response
   * @throws HttpServerErrorException If the request isn't good
   * @throws InterruptedException If the thread is interrupted in the process
   * @throws ExecutionException If the process did not execute properly
   * @throws URISyntaxException If the URI syntax is incorrect
   */
  private HttpResponse<String> get(final String endpoint) throws InterruptedException,
      ExecutionException, URISyntaxException, HttpServerErrorException {
    HttpResponse<String> response = this.getAsync(endpoint).get();

    if (response.statusCode() != HttpStatus.OK.value()) {
      throw new HttpServerErrorException(HttpStatus.valueOf(response.statusCode()),
          response.body());
    }
    return response;
  }

  /**
   * Sends an asynchronous HTTP GET request.
   *
   * @param endpoint The endpoint to fetch data from
   * @return Completable HTTP Response
   * @throws URISyntaxException If the URI syntax is incorrect
   */
  private CompletableFuture<HttpResponse<String>> getAsync(final String endpoint)
      throws URISyntaxException {
    HttpClient client = HttpClient.newBuilder().build();
    HttpRequest req =
        HttpRequest.newBuilder().GET().uri(new URI(this.url + ":" + this.port + endpoint)).build();
    return client.sendAsync(req, BodyHandlers.ofString());
  }

  /**
   * Sends a HTTP POST request.
   *
   * @param endpoint The endpoint to post data to
   * @param payload The data that will be posted
   * @return A HTTP Response
   * @throws HttpServerErrorException If the request isn't good
   * @throws InterruptedException If the thread is interrupted in the process
   * @throws ExecutionException If the process did not execute properly
   * @throws URISyntaxException If the URI syntax is incorrect
   */
  private HttpResponse<String> post(final String endpoint, final String payload)
      throws InterruptedException, ExecutionException, URISyntaxException,
      HttpServerErrorException {
    HttpResponse<String> response = this.postAsync(endpoint, payload).get();
    if (response.statusCode() != HttpStatus.OK.value()) {
      throw new HttpServerErrorException(HttpStatus.valueOf(response.statusCode()),
          response.body());
    }
    return response;
  }

  /**
   * Sends an asynchronous HTTP POST request.
   *
   * @param endpoint The endpoint to post data to
   * @param payload The data that will be posted
   * @return Completable HTTP Response
   * @throws URISyntaxException If the URI syntax is incorrect
   */
  private CompletableFuture<HttpResponse<String>> postAsync(final String endpoint,
      final String payload) throws URISyntaxException {
    HttpClient client = HttpClient.newBuilder().build();
    HttpRequest req = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(payload))
        .uri(new URI(this.url + ":" + this.port + endpoint)).build();
    return client.sendAsync(req, BodyHandlers.ofString());
  }

  /**
   * Sends a HTTP DELETE request.
   *
   * @param endpoint The endpoint to delete data from
   * @return A HTTP Response
   * @throws InterruptedException If the thread is interrupted in the process
   * @throws ExecutionException If the process did not execute properly
   * @throws URISyntaxException If the URI syntax is incorrect
   */
  private HttpResponse<String> delete(final String endpoint)
      throws InterruptedException, ExecutionException, URISyntaxException {
    HttpResponse<String> response = this.deleteAsync(endpoint).get();
    if (response.statusCode() != HttpStatus.OK.value()) {
      throw new HttpServerErrorException(HttpStatus.valueOf(response.statusCode()),
          response.body());
    }
    return response;
  }

  /**
   * Sends an asynchronous delete request.
   *
   * @param endpoint Location in the API
   * @return Completable HTTP Response
   * @throws URISyntaxException If the URI syntax is incorrect
   */
  private CompletableFuture<HttpResponse<String>> deleteAsync(final String endpoint)
      throws URISyntaxException {
    HttpClient client = HttpClient.newBuilder().build();
    HttpRequest req = HttpRequest.newBuilder().DELETE().uri(new URI(getUrl(endpoint))).build();
    return client.sendAsync(req, BodyHandlers.ofString());
  }

  /**
   * Returns a URL to the endpoint in the REST API.
   *
   * @param endpoint Location in the API
   * @return URL to the endpoint
   */
  private String getUrl(String endpoint) {
    return this.url + ":" + this.port + endpoint;
  }

}
