package uk.org.dft_bluebadge.infrastructure;

import uk.org.dft_bluebadge.ArgValidator;
import uk.org.dft_bluebadge.Arg;

public class PostgresConfiguration{
  private String host;
  private String database;
  private String user;
  private String password;

  public PostgresConfiguration(String host, String database, String user, String password){
    this.host = ArgValidator.validate(host, (x) -> Arg.NotNull(x), "host");
    this.database =  ArgValidator.validate(database, (x) -> Arg.NotNull(x), "database");
    this.user = ArgValidator.validate(user, (x) -> Arg.NotNull(x), "user");
    this. ***REMOVED***);    
  }
}
