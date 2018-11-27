
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import uk.org.dft_bluebadge.Base64CredentialFactory;
import uk.org.dft_bluebadge.Configuration;
import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.CredentialLink;
import uk.org.dft_bluebadge.infrastructure.AwsS3CredentialService;

public class AwsS3CredentialServiceTest {


  @Test 
  public void testGeneratesNewCredential() throws UnirestException{
    Credential cred = new Credential("A","B");
    AwsS3CredentialService classUnderTest = new AwsS3CredentialService(Configuration.awsRegion());
    CredentialLink link = classUnderTest.storeCredential(cred, Configuration.s3Bucket());

    String url = link.getUrl().toString();
    HttpResponse<String> response = Unirest.get(url).asString();

    String expected = "client_id=A\ ***REMOVED***
    assertThat(response.getBody(), is(equalTo(expected)));
  }

}

