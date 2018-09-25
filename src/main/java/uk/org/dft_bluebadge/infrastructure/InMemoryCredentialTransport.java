package uk.org.dft_bluebadge.infrastructure;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

import com.amazonaws.services.rekognition.model.S3Object;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.CredentialTransport;
import uk.org.dft_bluebadge.CredentialTransportModel;
import uk.org.dft_bluebadge.CredentialLink;
import uk.org.dft_bluebadge.EmailAddress;
import uk.org.dft_bluebadge.LocalAuthorityConsumer;

public class InMemoryCredentialTransport implements CredentialTransport{

  static final Logger LOG = Logger.getLogger(InMemoryCredentialTransport.class.getName());
  private CredentialTransportModel last;
  private AwsS3CredentialService awsS3CredentialService;


  public InMemoryCredentialTransport(){
    String clientRegion = "eu-west-2";
    this.awsS3CredentialService = new AwsS3CredentialService(clientRegion);
  }

  public Boolean send(LocalAuthorityConsumer consumer, Credential credential){
    String bucketName = "dev-uk-gov-dft-client-credentials";

    try { 
      
      CredentialLink link = this.awsS3CredentialService.storeCredential(credential, bucketName);
      this.last = new CredentialTransportModel(consumer, link);
      return true;
    } catch(AmazonServiceException ex) {
        LOG.log(Level.SEVERE, ex.getMessage(), ex);
    } catch(SdkClientException ex) {
        LOG.log(Level.SEVERE, ex.getMessage(), ex);
    } catch(RuntimeException ex){
        LOG.log(Level.SEVERE, ex.getMessage(), ex);
    }  
    return false;
  }

  public CredentialTransportModel getLast(){
    return this.last;
  }
}


