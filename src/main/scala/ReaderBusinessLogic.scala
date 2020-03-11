import cats.data.ReaderT
import cats.effect.{ExitCode, IO, IOApp}

object ReaderBusinessLogicApp extends IOApp {

  val conf = Configuration(
    DatabaseConfiguration("jdbc://....."),
    AwsConfiguration("access_key_id", "secret_access_key")
  )

  import ReaderBusinessLogic._

  val appLogic: Configured[ExitCode] = for {
    csv <- downloadExcelSheets(versionNumber = 1337)
    crunched = crunchNumbers(csv)
    _ <- storeResult(crunched)
  } yield ExitCode.Success

  override def run(args: List[String]): IO[ExitCode] = appLogic.run(conf)

}

object ReaderBusinessLogic {

  type Configured[T] = ReaderT[IO, Configuration, T]

  def downloadExcelSheets(versionNumber: Int): Configured[String] =
    for {
      configuration <- ReaderT.ask[IO, Configuration]
      _             <- ReaderT.liftF(IO.delay(println(s"Downloading csv with conf: ${configuration.awsConfiguration}")))
    } yield "this, is, csv, data"

  def crunchNumbers(csv: String): Double = csv.count(_ == ',')

  def storeResult(crunchedNumbers: Double): Configured[Unit] =
    for {
      configuration <- ReaderT.ask[IO, Configuration]
      _             <- ReaderT.liftF(IO.delay(println(s"Inserting rows with conf: ${configuration.dbConfiguration}")))
    } yield ()

}
