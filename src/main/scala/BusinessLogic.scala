object BusinessLogicApp extends App {

  val conf = Configuration(
    DatabaseConfiguration("jdbc://....."),
    AwsConfiguration("access_key_id", "secret_access_key")
  )

  val businessLogic = new BusinessLogic(conf)

  val csv = businessLogic.downloadExcelSheets(1337)
  val crunched = businessLogic.crunchNumbers(csv)
  businessLogic.storeResult(crunched)

}

class BusinessLogic(configuration: Configuration) {


  def downloadExcelSheets(versionNumber: Int): String = {
    println(
      s"Downloading excel sheets (using aws conf: ${configuration.awsConfiguration})"
    )
    "this, is, csv, data"
  }

  def crunchNumbers(csv: String): Double = {
    println(s"Crunching numbers: ${csv}")
    csv.count(_ == ',').toDouble
  }

  def storeResult(crunchedNumbers: Double): Unit =
    println(
      s"Inserting numbers in database (using database conf: ${configuration.dbConfiguration})"
    )

}
