package uk.org.dft_bluebadge;

public interface CredentialTransport{
  public Boolean send(LocalAuthorityConsumer consumer, Credential message);
}
