import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import org.mindrot.jbcrypt.BCrypt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import uk.org.dft_bluebadge.Base64CredentialFactory;
import uk.org.dft_bluebadge.Configuration;
import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.CredentialFactory;
import uk.org.dft_bluebadge.CredentialLink;
import uk.org.dft_bluebadge.EmailAddress;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;
import uk.org.dft_bluebadge.infrastructure.AwsS3CredentialService;
import uk.org.dft_bluebadge.infrastructure.PostgresCredentialService;

class SimpleCredentialFactory implements CredentialFactory{
  public static final String CLIENT_ID="A";
  public static final String CLIENT_SECRET="B";

  public Credential generate(){
    return new Credential(CLIENT_ID, CLIENT_SECRET);
  }
}

public class PostgresCredentialServiceTest {

  @Test 
  public void testStoresCredentialInDatabase() throws ClassNotFoundException, SQLException{
    EmailAddress address = new EmailAddress("a@a.com");
    LocalAuthorityConsumer consumer = new LocalAuthorityConsumer(address, "ABERD");

    CredentialFactory credentialFactory = new SimpleCredentialFactory();
    PostgresCredentialService classUnderTest = new PostgresCredentialService(credentialFactory);
  
    classUnderTest.generate(consumer); 
    
    try(Connection connection = Configuration.getDbConnection()){
      String SQL = "SELECT client_id, client_secret, local_authority_short_code, active from usermanagement.client_credentials where client_id = ?";
      try(PreparedStatement pstmt = connection.prepareStatement(SQL)){
        pstmt.setString(1, SimpleCredentialFactory.CLIENT_ID);

        try(ResultSet rs = pstmt.executeQuery()){
          if(rs.next()){
            String clientSecret = rs.getString("client_secret");

            assertThat(BCrypt.checkpw(SimpleCredentialFactory.CLIENT_SECRET, clientSecret), is(equalTo(true)));
          }else{
            throw new RuntimeException("No Data");
          }
        }
      }
    }
  }

}
