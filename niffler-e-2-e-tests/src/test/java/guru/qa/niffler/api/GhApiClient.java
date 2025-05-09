package guru.qa.niffler.api;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.niffler.config.Config;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import java.util.Objects;

public class GhApiClient {

  private static final String GH_TOKEN_ENV = "GITHUB_TOKEN";

  private final Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(Config.getInstance().ghUrl())
      .addConverterFactory(JacksonConverterFactory.create())
      .build();

  private final GhApi ghApi = retrofit.create(GhApi.class);

  @SneakyThrows
  public String issueState(String issueNumber) {
    JsonNode responce = ghApi.issue(
            "Bearer " + System.getenv(GH_TOKEN_ENV),
            issueNumber)
                             .execute()
                             .body();
    return Objects.requireNonNull(responce).get("state").asText();
  }
}
