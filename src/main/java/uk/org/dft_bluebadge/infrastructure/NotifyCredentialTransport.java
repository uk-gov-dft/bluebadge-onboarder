package uk.org.dft_bluebadge.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;
import uk.org.dft_bluebadge.Configuration;
import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.CredentialLink;
import uk.org.dft_bluebadge.CredentialTransport;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;

public class NotifyCredentialTransport implements CredentialTransport {

    static final Logger LOG = Logger.getLogger(NotifyCredentialTransport.class.getName());

    private AwsS3CredentialService awsS3CredentialService;
    private NotificationClient client;

    public NotifyCredentialTransport(String apiKey){
        String clientRegion = Configuration.awsRegion();
        this.awsS3CredentialService = new AwsS3CredentialService(clientRegion);
        this.client = new NotificationClient(apiKey); 
    }

    public Boolean send(LocalAuthorityConsumer consumer, Credential credential){
        String bucketName = Configuration.s3Bucket();
        CredentialLink link = this.awsS3CredentialService.storeCredential(credential, bucketName);

        Map<String, String> personalisation = new HashMap<>();
        personalisation.put("emailAddress", consumer.getEmail().getValue());
        personalisation.put("localAuthority", consumer.getShortCode()); 
        personalisation.put("apiLink", link.getUrl().toString()); 
        personalisation.put("urlExpiry", link.getExpiry().toString()); 

        try{
            this.client.sendEmail(
                    Configuration.notifyTemplateId(),
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
