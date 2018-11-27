package uk.org.dft_bluebadge;

import java.net.URL;
import java.util.Date;

public class CredentialLink {
  private URL url;
  private Date expiry;

  public CredentialLink(URL url, Date expiry){
    this.url = url;
    this.expiry = expiry;
  }

  public URL getUrl(){
    return this.url;
  }

  public Date getExpiry(){
    return this.expiry;
  }
}
