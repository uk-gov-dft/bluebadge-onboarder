/*
 * This Java source file was generated by the Gradle 'init' task.
 */


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import uk.org.dft_bluebadge.Base64CredentialFactory;
import uk.org.dft_bluebadge.CredentialFactoryHarness;
import uk.org.dft_bluebadge.CredentialService;
import uk.org.dft_bluebadge.EmailAddress;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;
import uk.org.dft_bluebadge.Onboarder;
import uk.org.dft_bluebadge.infrastructure.InMemoryCredentialTransport;
import uk.org.dft_bluebadge.infrastructure.PostgresCredentialService;

public class AppTest {

  static final Logger LOG = Logger.getLogger(AppTest.class.getName());

  @Test 
  public void test_something_does_something() 
    throws ClassNotFoundException, SQLException, UnirestException{

    Base64CredentialFactory base64Factory = new Base64CredentialFactory();
    CredentialFactoryHarness factory = new CredentialFactoryHarness(base64Factory);
    CredentialService service = new PostgresCredentialService(factory);
    InMemoryCredentialTransport transport = new InMemoryCredentialTransport();
    Onboarder classUnderTest = new Onboarder(service, transport);

    EmailAddress email = new EmailAddress("john.doe@does.not.exist");
    String shortCode = "ABERD";
    LocalAuthorityConsumer consumer = new LocalAuthorityConsumer(email, shortCode);
    classUnderTest.onboard(consumer);

    Class.forName("org.postgresql.Driver");
    Properties connectionProps = new Properties();
    connectionProps.put("user", "developer");
    connectionProps.put(" ***REMOVED***);

    try(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bb_dev", connectionProps)){
      String SQL = "SELECT client_id, client_secret, local_authority_short_code, active from usermanagement.client_credentials where client_id = ?";
      try(PreparedStatement pstmt = connection.prepareStatement(SQL)){
        pstmt.setString(1, factory.getLast().getClientID());

        try(ResultSet rs = pstmt.executeQuery()){
          if(rs.next()){
            String clientId = rs.getString("client_id");
            String clientSecret = rs.getString("client_secret");

            assertTrue(BCrypt.checkpw(factory.getLast().getClientSecret(), clientSecret), "Secret Hash not equal");
          }else{
            throw new RuntimeException("No Data");
          }
        }
      }
    }

    String url = transport.getLast().getLink().getUrl().toString();
    HttpResponse<String> response = Unirest.get(url).asString();

    assertNotNull(response.getBody());

    LOG.info(response.getBody());
  }

}
