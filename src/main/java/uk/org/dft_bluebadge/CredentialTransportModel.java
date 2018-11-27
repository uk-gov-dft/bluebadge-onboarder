package uk.org.dft_bluebadge;


import uk.org.dft_bluebadge.CredentialLink;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;

public class CredentialTransportModel{
  
  private LocalAuthorityConsumer consumer;
  private CredentialLink link;

  public CredentialTransportModel(LocalAuthorityConsumer consumer, CredentialLink link)
  {
    this.consumer = consumer;
    this.link = link;
  }

  public LocalAuthorityConsumer getConsumer(){
    return this.consumer;
  }

  public CredentialLink getLink(){
    return this.link;
  }

}
