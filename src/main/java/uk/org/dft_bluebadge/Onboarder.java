package uk.org.dft_bluebadge;

import uk.org.dft_bluebadge.LocalAuthorityConsumer;

public class Onboarder{

  private CredentialService service;
  private CredentialTransport transport;

  public Onboarder(CredentialService service, CredentialTransport transport){
    this.service = service;
    this.transport = transport;
  }

  public void onboard(LocalAuthorityConsumer consumer){
    Credential credential = this.service.generate(consumer);

    this.transport.send(consumer, credential);
  }
}
