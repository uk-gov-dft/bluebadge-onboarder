package uk.org.dft_bluebadge.infrastructure;


import java.util.logging.Level;
import java.util.logging.Logger;

import uk.org.dft_bluebadge.Configuration;
import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.CredentialLink;
import uk.org.dft_bluebadge.CredentialTransport;
import uk.org.dft_bluebadge.CredentialTransportModel;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;
import uk.org.dft_bluebadge.OnboarderApplicationException;

public class InMemoryCredentialTransport implements CredentialTransport{

  static final Logger LOG = Logger.getLogger(InMemoryCredentialTransport.class.getName());
  private CredentialTransportModel last;
  private AwsS3CredentialService awsS3CredentialService;


  public InMemoryCredentialTransport(){
    String clientRegion = Configuration.awsRegion();
    this.awsS3CredentialService = new AwsS3CredentialService(clientRegion);
  }

  public Boolean send(LocalAuthorityConsumer consumer, Credential credential){
    String bucketName = Configuration.s3Bucket();

    try { 
      CredentialLink link = this.awsS3CredentialService.storeCredential(credential, bucketName);
      this.last = new CredentialTransportModel(consumer, link);
      return true;
    } catch(OnboarderApplicationException ex){
        LOG.log(Level.SEVERE, ex.getMessage(), ex);
    }  
    return false;
  }

  public CredentialTransportModel getLast(){
    return this.last;
  }
}


