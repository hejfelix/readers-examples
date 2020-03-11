case class DatabaseConfiguration(jdbcUrl: String)
case class AwsConfiguration(accessKeyId: String, secretAccessKey: String)

case class Configuration(dbConfiguration: DatabaseConfiguration, awsConfiguration: AwsConfiguration)
