import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.EmailAddress;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;
import uk.org.dft_bluebadge.infrastructure.NotifyCredentialTransport;

public class NotifyCredentialTransportTest {


  @Test 
  public void test_get_successful_response(){
    String testApiKey = "test__live_api_creds-70a18c16-bfa1-4d8c-83ac-c8d16f425ea7-3f31435d-07ea-4cd1-b373-a50e93441628";
    NotifyCredentialTransport transport = new NotifyCredentialTransport(testApiKey); 
    EmailAddress address = new EmailAddress("andy.rea@does.not.exist");
    LocalAuthorityConsumer consumer = new LocalAuthorityConsumer(address,"ABERD");
    Credential cred = new Credential("ID","SECRET");
    transport.send(consumer, cred);
  }

}
