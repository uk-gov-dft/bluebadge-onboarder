package uk.org.dft_bluebadge;

public class Configuration{

  private static final String NOTIFY_API_KEY_KEY="NOTIFY_API_KEY"; 

  public static final String NOTIFY_API_KEY(){
    return System.getenv(NOTIFY_API_KEY_KEY);
  }

  private static final String AWS_REGION_KEY="AWS_REGION"; 

  public static final String AWS_REGION(){
    return System.getenv(AWS_REGION_KEY);
  }

  private static final String S3_BUCKET_KEY="S3_BUCKET"; 

  public static final String S3_BUCKET(){
    return System.getenv(S3_BUCKET_KEY);
  }

  private static final String NOTIFY_TEMPLATE_ID_KEY="NOTIFY_TEMPLATE_ID"; 

  public static final String NOTIFY_TEMPLATE_ID(){
    return System.getenv(NOTIFY_TEMPLATE_ID_KEY);
  }

  private static final String DB_USER_KEY="DB_USER"; 

  public static final String DB_USER() {
    return System.getenv(DB_USER_KEY);
  }

  private static final String DB_PASSWORD_KEY="DB_PASSWORD"; 

  public static final String DB_PASSWORD() {
    return System.getenv(DB_PASSWORD_KEY);
  }

  private static final String DB_HOST_KEY="DB_HOST"; 

  public static final String DB_HOST() {
    return System.getenv(DB_HOST_KEY);
  }

  private static final String DB_PORT_KEY="DB_PORT"; 

  public static final String DB_PORT() {
    return System.getenv(DB_PORT_KEY);
  }

  private static final String DB_NAME_KEY="DB_NAME"; 

  public static final String DB_NAME() {
    return System.getenv(DB_NAME_KEY);
  }
} 
