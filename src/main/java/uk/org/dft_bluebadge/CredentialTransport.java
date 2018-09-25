package uk.org.dft_bluebadge;

public interface CredentialTransport{
  void send(EmailAddress email, Credential message);
}
