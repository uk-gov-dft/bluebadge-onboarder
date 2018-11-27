package uk.org.dft_bluebadge.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;

import uk.org.dft_bluebadge.Configuration;
import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.CredentialFactory;
import uk.org.dft_bluebadge.CredentialService;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;
import uk.org.dft_bluebadge.OnboarderApplicationException;

public class PostgresCredentialService implements CredentialService{

  static final Logger LOG = Logger.getLogger(PostgresCredentialService.class.getName());
  private CredentialFactory factory;

  public PostgresCredentialService(CredentialFactory factory){
    this.factory = factory;
  }

  public Credential generate(LocalAuthorityConsumer consumer) {

    Credential credential = this.factory.generate();

    try{
      Class.forName("org.postgresql.Driver");

      insertCredential(consumer, credential);

      return credential;
    }catch(ClassNotFoundException ex){
      LOG.log(Level.SEVERE, ex.getMessage(), ex);
    }
    throw new OnboarderApplicationException("Failed to store credential in database");
  }

  private void insertCredential(LocalAuthorityConsumer consumer, Credential credential){
      Properties connectionProps = new Properties();
      connectionProps.put("user", Configuration.dbUser());
      connectionProps.put("password", Configuration.dbPassword());

      String dbHost = Configuration.dbHost();
      String dbPort = Configuration.dbPort();
      String dbName = Configuration.dbName();
      String dbUrl = String.format("jdbc:postgresql://%s:%s/%s", dbHost, dbPort, dbName);

      try(Connection connection = DriverManager.getConnection(dbUrl, connectionProps)){
        String sql = "INSERT INTO usermanagement.client_credentials (client_id, client_secret, local_authority_short_code, active, creation_timestamp, expiry_timestamp) VALUES (?,?,?,?,?,?)";
        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
          pstmt.setString(1, credential.getClientID());

          String hashedSecret = BCrypt.hashpw(credential.getClientSecret(), BCrypt.gensalt(12));
          pstmt.setString(2, hashedSecret);
          pstmt.setString(3, consumer.getShortCode());
          pstmt.setBoolean(4, true);

          Calendar cal = GregorianCalendar.getInstance();
          pstmt.setDate(5, new java.sql.Date(cal.getTime().getTime()));

          cal.add(Calendar.YEAR, 1);
          java.util.Date expiry = cal.getTime();
          pstmt.setDate(6, new java.sql.Date(expiry.getTime()));

          pstmt.execute();
        }
      }catch(SQLException ex){
        LOG.log(Level.SEVERE, ex.getMessage(), ex);
      }
  }

}
