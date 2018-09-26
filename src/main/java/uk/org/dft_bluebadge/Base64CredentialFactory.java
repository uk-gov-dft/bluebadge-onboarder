package uk.org.dft_bluebadge;

import java.security.SecureRandom;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;

public class Base64CredentialFactory implements CredentialFactory{
  static final Logger LOG = Logger.getLogger(Base64CredentialFactory.class.getName());

  public Credential generate(){
    SecureRandom random = new SecureRandom();
    byte[] clientIdBytes = new byte[24];
    random.nextBytes(clientIdBytes);

    String clientId = Base64.encodeBase64URLSafeString(clientIdBytes);

    byte[] clientSecretBytes = new byte[48];
    random.nextBytes(clientSecretBytes);
    String clientSecret = Base64.encodeBase64URLSafeString(clientSecretBytes);

    return new Credential(clientId, clientSecret);
  }
}
