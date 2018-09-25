package uk.org.dft_bluebadge;

public class CredentialFactoryHarness implements CredentialFactory{
  private Credential expected;

  public CredentialFactoryHarness(Credential expected){
    this.expected = expected;
  }

  public Credential generate(){
    return this.expected;
  }
}
