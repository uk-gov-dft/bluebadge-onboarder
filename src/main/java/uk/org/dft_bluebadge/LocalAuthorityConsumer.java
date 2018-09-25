package uk.org.dft_bluebadge;

public class LocalAuthorityConsumer{

  private String email;
  private String shortCode;

  public LocalAuthorityConsumer(String email, String shortCode){
    this.email = email;
    this.shortCode = shortCode; 
  }

  public String getEmail(){
    return this.email;
  }

  public String getShortCode(){
    return this.shortCode;
  }
}

