package uk.org.dft_bluebadge;

import com.google.gson.Gson;
import static spark.Spark.*;
import uk.org.dft_bluebadge.Base64CredentialFactory;
import uk.org.dft_bluebadge.CredentialFactoryHarness;
import uk.org.dft_bluebadge.CredentialService;
import uk.org.dft_bluebadge.EmailAddress;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;
import uk.org.dft_bluebadge.Onboarder;
import uk.org.dft_bluebadge.infrastructure.JsonTransformer;
import uk.org.dft_bluebadge.infrastructure.PostgresCredentialService;
import uk.org.dft_bluebadge.infrastructure.NotifyCredentialTransport;

public class App {
  public String getGreeting() {
    return "Hello world.";
  }

  public static void main(String[] args) {
    Gson gson = new Gson();
    get("/", (req, res) -> {
      return "SUCCESS";
    });

    post("/apiConsumers", (req, res) -> {
      CreateApiConsumerRequest request = 
        gson.fromJson(req.body(), CreateApiConsumerRequest.class);

      String apiKey = Configuration.NOTIFY_API_KEY();
      CredentialFactory base64Factory = new Base64CredentialFactory();
      CredentialService service = new PostgresCredentialService(base64Factory);
      CredentialTransport transport = new NotifyCredentialTransport(apiKey); 
      Onboarder application = new Onboarder(service, transport);

      EmailAddress email = new EmailAddress(request.getEmailAddress());
      String shortCode = request.getLocalAuthorityCode();
      LocalAuthorityConsumer consumer = new LocalAuthorityConsumer(email, shortCode);
      application.onboard(consumer);

      CreateApiConsumerResponse response = new CreateApiConsumerResponse();
      response.setSuccess(true);
      return response;
    }, new JsonTransformer());
  }
}
