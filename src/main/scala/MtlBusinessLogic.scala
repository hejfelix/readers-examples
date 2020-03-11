import MtlBusinessLogic._
import cats.data.ReaderT
import cats._
import cats.effect._
import cats.mtl._
import cats.mtl.implicits._
import cats.implicits._

object MtlBusinessLogicApp extends IOApp {
  val config = Configuration(
    DatabaseConfiguration("jdbc://....."),
    AwsConfiguration("access_key_id", "secret_access_key")
  )

  implicit def hasAwsConfig[F[_]: Functor: ApplicativeAsk[*[_], Configuration]]: ApplicativeAsk[F, AwsConfiguration] =
    new DefaultApplicativeAsk[F, AwsConfiguration] {
      val applicative: Applicative[F] = ApplicativeAsk[F, Configuration].applicative
      def ask: F[AwsConfiguration]    = ApplicativeAsk[F, Configuration].ask.map(_.awsConfiguration)
    }

  implicit def hasDatabaseConfig[F[_]: Functor: ApplicativeAsk[*[_], Configuration]]
      : ApplicativeAsk[F, DatabaseConfiguration] =
    new DefaultApplicativeAsk[F, DatabaseConfiguration] {
      val applicative: Applicative[F]   = ApplicativeAsk[F, Configuration].applicative
      def ask: F[DatabaseConfiguration] = ApplicativeAsk[F, Configuration].ask.map(_.dbConfiguration)
    }

  def program[F[_]: Sync: ApplicativeAsk[*[_], Configuration]] =
    for {
      csv <- downloadExcelSheets(1337)
      _   <- storeResult(crunchNumbers(csv))
    } yield ExitCode.Success

  def run(args: List[String]): IO[ExitCode] =
    program[ReaderT[IO, Configuration, *]].run(config)
}

object MtlBusinessLogic {
  def downloadExcelSheets[F[_]](
      versionNumber: Int
  )(implicit F: Sync[F], config: ApplicativeAsk[F, AwsConfiguration]) =
    for {
      configuration <- config.ask
      _             <- F.delay(println(s"Downloading csv with conf: ${configuration}"))
    } yield "this, is, csv, data"

  def crunchNumbers(csv: String): Double = csv.count(_ == ',')

  def storeResult[F[_]](
      crunchedNumbers: Double
  )(implicit F: Sync[F], config: ApplicativeAsk[F, DatabaseConfiguration]): F[Unit] =
    for {
      configuration <- config.ask
      _             <- F.delay(println(s"Inserting rows with conf: ${configuration}"))
    } yield ()
}
