import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.org.dft_bluebadge.Configuration;
import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.EmailAddress;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;
import uk.org.dft_bluebadge.infrastructure.NotifyCredentialTransport;

public class NotifyCredentialTransportTest {


  @Test 
  public void test_get_successful_response(){
    String testApiKey = Configuration.notifyApiKey();
    NotifyCredentialTransport transport = new NotifyCredentialTransport(testApiKey); 
    EmailAddress address = new EmailAddress("andy.rea@does.not.exist");
    LocalAuthorityConsumer consumer = new LocalAuthorityConsumer(address,"ABERD");
    Credential cred = new Credential("ID","SECRET");
    Boolean result = transport.send(consumer, cred);
    assertTrue(result);
  }

}
