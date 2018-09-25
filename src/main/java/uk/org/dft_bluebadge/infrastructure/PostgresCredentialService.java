package uk.org.dft_bluebadge.infrastructure;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;

import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.CredentialFactory;
import uk.org.dft_bluebadge.CredentialService;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;

public class PostgresCredentialService implements CredentialService{

  static final Logger LOG = Logger.getLogger(PostgresCredentialService.class.getName());
  private CredentialFactory factory;

  public PostgresCredentialService(CredentialFactory factory){
    this.factory = factory;
  }

  public Credential generate(LocalAuthorityConsumer consumer) throws RuntimeException{

    Credential credential = this.factory.generate();

    LOG.info(credential.getClientID());
    Connection connection = null;

    try{
      Class.forName("org.postgresql.Driver");
      Properties connectionProps = new Properties();
      connectionProps.put("user", "developer");
      connectionProps.put(" ***REMOVED***);
      connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bb_dev", connectionProps);
      String SQL = "INSERT INTO usermanagement.client_credentials (client_id, client_secret, local_authority_short_code, active, creation_timestamp, expiry_timestamp) VALUES (?,?,?,?,?,?)";
      PreparedStatement pstmt = connection.prepareStatement(SQL);
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

      Boolean success = pstmt.execute();
      return credential;
    }catch(SQLException ex){
      LOG.log(Level.SEVERE, ex.getMessage(), ex);
    }catch(ClassNotFoundException ex){
      LOG.log(Level.SEVERE, ex.getMessage(), ex);
    }
    finally{
      try{
        connection.close();
      }catch(SQLException ex){
        LOG.log(Level.SEVERE, ex.getMessage(), ex);
      }
    }
    throw new RuntimeException("Failed to store credential in database");
  }

}
