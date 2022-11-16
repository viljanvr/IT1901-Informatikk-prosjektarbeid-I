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

  public HashMap<String, Recipe> getRecipes() throws HttpServerErrorException, InterruptedException,
      ExecutionException, URISyntaxException {
    HttpResponse<String> response = this.get(getUrl("/api/v1/recipes"));
    return getMapFromHttpResponse(response);
  }

  public Recipe getRecipe(final String id) throws HttpServerErrorException, InterruptedException,
      ExecutionException, URISyntaxException {
    HttpResponse<String> response = this.get(getUrl("/api/v1/recipes" + id));
    return (Recipe) getMapFromHttpResponse(response).get(id);
  }

  public String addRecipe(Recipe recipe) throws HttpServerErrorException, InterruptedException,
      ExecutionException, URISyntaxException {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String payload = gson.toJson(recipe);
    HttpResponse<String> response = this.post(getUrl("/api/v1/recipes/add"), payload);
    return gson.fromJson(response.body(), JsonObject.class).get("id").getAsString();
  }

  public void deleteRecipe(String id)
      throws InterruptedException, ExecutionException, URISyntaxException {
    this.delete("/api/v1/recipes/" + id);
  }

  private HashMap<String, Recipe> getMapFromHttpResponse(final HttpResponse<String> response) {
    Gson gson = new Gson();
    Type mapType = new TypeToken<HashMap<String, Recipe>>() {}.getType();
    return gson.fromJson(response.body(), mapType);

  }

  private HttpResponse<String> get(final String endpoint) throws InterruptedException,
      ExecutionException, URISyntaxException, HttpServerErrorException {
    HttpResponse<String> response = this.getAsync(endpoint).get();

    if (response.statusCode() != HttpStatus.OK.value()) {
      throw new HttpServerErrorException(HttpStatus.valueOf(response.statusCode()),
          response.body());
    }
    return response;
  }

  private CompletableFuture<HttpResponse<String>> getAsync(final String endpoint)
      throws URISyntaxException {
    HttpClient client = HttpClient.newBuilder().build();
    HttpRequest req =
        HttpRequest.newBuilder().GET().uri(new URI(this.url + ":" + this.port + endpoint)).build();
    return client.sendAsync(req, BodyHandlers.ofString());
  }

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

  private CompletableFuture<HttpResponse<String>> postAsync(final String endpoint,
      final String payload) throws URISyntaxException {
    HttpClient client = HttpClient.newBuilder().build();
    HttpRequest req = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(payload))
        .uri(new URI(this.url + ":" + this.port + endpoint)).build();
    return client.sendAsync(req, BodyHandlers.ofString());
  }

  private HttpResponse<String> delete(final String endpoint)
      throws InterruptedException, ExecutionException, URISyntaxException {
    HttpResponse<String> response = this.deleteAsync(endpoint).get();
    if (response.statusCode() != HttpStatus.OK.value()) {
      throw new HttpServerErrorException(HttpStatus.valueOf(response.statusCode()),
          response.body());
    }
    return response;
  }

  private CompletableFuture<HttpResponse<String>> deleteAsync(final String endpoint)
      throws URISyntaxException {
    HttpClient client = HttpClient.newBuilder().build();
    HttpRequest req = HttpRequest.newBuilder().DELETE().uri(new URI(getUrl(endpoint))).build();
    return client.sendAsync(req, BodyHandlers.ofString());
  }


  private String getUrl(String endpoint) {
    return this.url + ":" + this.port + endpoint;
  }


}
