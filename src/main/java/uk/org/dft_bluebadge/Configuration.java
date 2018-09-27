package uk.org.dft_bluebadge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Configuration{

  public Configuration(){}

  public static final String NOTIFY_API_KEY_KEY="NOTIFY_API_KEY"; 

  public static final String notifyApiKey(){
    return System.getenv(NOTIFY_API_KEY_KEY);
  }

  public static final String AWS_REGION_KEY="AWS_REGION"; 

  public static final String awsRegion(){
    return System.getenv(AWS_REGION_KEY);
  }

  public static final String S3_BUCKET_KEY="S3_BUCKET"; 

  public static final String s3Bucket(){
    return System.getenv(S3_BUCKET_KEY);
  }

  public static final String NOTIFY_TEMPLATE_ID_KEY="NOTIFY_TEMPLATE_ID"; 

  public static final String notifyTemplateId(){
    return System.getenv(NOTIFY_TEMPLATE_ID_KEY);
  }

  public static final String DB_USER_KEY="DB_USER"; 

  public static final String dbUser() {
    return System.getenv(DB_USER_KEY);
  }

  public static final String DB_PW_KEY="DB_PASSWORD"; 

  public static final String dbPassword() {
    return System.getenv(DB_PW_KEY);
  }

  public static final String DB_HOST_KEY="DB_HOST"; 

  public static final String dbHost() {
    return System.getenv(DB_HOST_KEY);
  }

  public static final String DB_PORT_KEY="DB_PORT"; 

  public static final String dbPort() {
    return System.getenv(DB_PORT_KEY);
  }

  public static final String DB_NAME_KEY="DB_NAME"; 

  public static final String dbName() {
    return System.getenv(DB_NAME_KEY);
  }

  public static String dbConnectionString(){
    return String.format("jdbc:postgresql://%s:%s/%s",
        dbHost(),
        dbPort(),
        dbName());
  }

  public static Connection getDbConnection() throws ClassNotFoundException, SQLException{
    String connectionString = Configuration.dbConnectionString();
    Class.forName("org.postgresql.Driver");
    Properties connectionProps = new Properties();
    connectionProps.put("user", dbUser());
    connectionProps.put("password", dbPassword());

    return DriverManager.getConnection(connectionString, connectionProps);
  }
} 
