package uk.org.dft_bluebadge;

public class Credential{

  private String clientID;
  private String clientSecret;

  public Credential(String clientID, String clientSecret){
    this.clientID = clientID;
    this.clientSecret = clientSecret;
  }

  public String getClientID(){
    return this.clientID;
  }

  public String getClientSecret(){
    return this.clientSecret;
  }
}
