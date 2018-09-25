package uk.org.dft_bluebadge;

import uk.org.dft_bluebadge.EmailAddress;

public class LocalAuthorityConsumer{

  private EmailAddress email;
  private String shortCode;

  public LocalAuthorityConsumer(EmailAddress email, String shortCode){
    this.email = email;
    this.shortCode = shortCode; 
  }

  public EmailAddress getEmail(){
    return this.email;
  }

  public String getShortCode(){
    return this.shortCode;
  }
}

