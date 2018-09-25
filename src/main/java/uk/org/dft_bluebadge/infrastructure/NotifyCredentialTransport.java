package uk.org.dft_bluebadge.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;
import uk.gov.service.notify.SendEmailResponse;

import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.CredentialTransport;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;

public class NotifyCredentialTransport implements CredentialTransport {

  static final Logger LOG = Logger.getLogger(NotifyCredentialTransport.class.getName());

  private NotificationClient client;

  public NotifyCredentialTransport(String apiKey){
    this.client = new NotificationClient(apiKey); 
  }

  public Boolean send(LocalAuthorityConsumer consumer, Credential message){
    Map<String, String> personalisation = new HashMap<>();
    personalisation.put("emailAddress", "johny@does.not.exist");
    personalisation.put("localAuthority", "ABERD"); 
    personalisation.put("apiLink", "http://does.not.exist"); 
    personalisation.put("urlExpiry", "Some time in the future"); 

    try{
    SendEmailResponse response = this.client.sendEmail(
        "e2ec9749-9ed2-4cb1-a506-10dc20ab9151",
        consumer.getEmail().getValue(),
        personalisation,
        UUID.randomUUID().toString(),
        "");
      return true;
    }catch(NotificationClientException ex){
      LOG.log(Level.SEVERE, ex.getMessage(), ex);
      return false;
    }

  }
}
