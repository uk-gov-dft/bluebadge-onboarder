package uk.org.dft_bluebadge.infrastructure;

import java.util.ArrayList;

import uk.org.dft_bluebadge.EmailAddress;
import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.CredentialTransport;

public class InMemoryCredentialTransport implements CredentialTransport{

  private ArrayList<Credential> sent;

  public InMemoryCredentialTransport(){
    this.sent = new ArrayList<Credential>();
  }
  
  public void send(EmailAddress email, Credential credential){
    
  }
}


