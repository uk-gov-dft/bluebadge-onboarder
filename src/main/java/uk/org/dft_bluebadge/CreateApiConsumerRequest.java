package uk.org.dft_bluebadge;

public class CreateApiConsumerRequest {
  private String emailAddress;
  private String localAuthorityCode;

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getEmailAddress() {
    return this.emailAddress;
  }

  public void setLocalAuthorityCode(String localAuthorityCode) {
    this.localAuthorityCode = localAuthorityCode;
  }

  public String getLocalAuthorityCode() {
    return this.localAuthorityCode;
  }
}
