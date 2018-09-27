import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import uk.org.dft_bluebadge.Base64CredentialFactory;
import uk.org.dft_bluebadge.Credential;

public class Base64CredentialFactoryTest {


  @Test 
  public void testGeneratesNewCredential(){
      Base64CredentialFactory classUnderTest = new Base64CredentialFactory();
      Credential credential = classUnderTest.generate();

      assertThat(credential.getClientID().length(), is(equalTo(32)));
      assertThat(credential.getClientSecret().length(), is(equalTo(64)));
  }
}
