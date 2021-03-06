package uk.org.dft_bluebadge.infrastructure;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.org.dft_bluebadge.Credential;
import uk.org.dft_bluebadge.CredentialLink;
import uk.org.dft_bluebadge.OnboarderApplicationException;

public class AwsS3CredentialService {

  static final Logger LOG = Logger.getLogger(AwsS3CredentialService.class.getName());
  private AmazonS3 s3Client;

  public AwsS3CredentialService(String region){
      this.s3Client = AmazonS3ClientBuilder.standard()
        .withRegion(region)
        .withCredentials(new DefaultAWSCredentialsProviderChain())
        .build();
  }

  public CredentialLink storeCredential(Credential credential, String bucketName) {
    try { 
      UUID uuid = UUID.randomUUID();
      String objectKey = "client_credentials_"+uuid.toString() + ".txt";
      String content = "client_id="+credential.getClientID()+"\n"+
          " ***REMOVED***
      byte[] contentAsBytes = content.getBytes();
      ByteArrayInputStream contentsAsStream = new ByteArrayInputStream(contentAsBytes);
      ObjectMetadata md = new ObjectMetadata();
      md.setContentLength(contentAsBytes.length);
      s3Client.putObject(new PutObjectRequest(bucketName, objectKey, contentsAsStream, md));

      // Set the presigned URL to expire after one hour.
      java.util.Date expiration = new java.util.Date();
      long expTimeMillis = expiration.getTime();
      expTimeMillis += 1000 * 60 * 60 * 24;
      expiration.setTime(expTimeMillis);

      // Generate the presigned URL.
      GeneratePresignedUrlRequest generatePresignedUrlRequest = 
        new GeneratePresignedUrlRequest(bucketName, objectKey)
        .withMethod(HttpMethod.GET)
        .withExpiration(expiration);
      URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

      return new CredentialLink(url, new Date(expTimeMillis));
    }
    catch(AmazonServiceException ex) {
        LOG.log(Level.SEVERE, "AWS_SEVICE_EXCEPTION", ex);
    }
    catch(SdkClientException ex) {
        LOG.log(Level.SEVERE, "SDK_CLIENT_EXCEPTION", ex);
    }
    throw new OnboarderApplicationException("Failed to store credential in AWS S3");
  }
}
