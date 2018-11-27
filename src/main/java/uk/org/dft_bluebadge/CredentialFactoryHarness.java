package uk.org.dft_bluebadge;

public class CredentialFactoryHarness implements CredentialFactory{

  private CredentialFactory wrapped;
  private Credential last;

  public CredentialFactoryHarness(CredentialFactory wrapped){
    this.wrapped = wrapped;
  }

  public Credential generate(){
    this.last = this.wrapped.generate();
    return this.last;
  }

  public Credential getLast(){
    return this.last;
  }
}
