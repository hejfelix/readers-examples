import MeowMtlBusinessLogic.crunchNumbers
import cats.Monad
import cats.effect.concurrent.Ref
import cats.effect.{ExitCode, IO, IOApp, Sync}
import cats.mtl.ApplicativeAsk
import cats.implicits._
import com.olegpy.meow.hierarchy._
import com.olegpy.meow.effects._

object MeowMtlBusinessLogicApp extends IOApp {

  val conf = Configuration(
    DatabaseConfiguration("jdbc://....."),
    AwsConfiguration("access_key_id", "secret_access_key")
  )

  def withEnv: IO[ExitCode] =
    for {
      ref    <- Ref.of[IO, Configuration](conf)
      result <- ref.runAsk(implicit conf => appLogic[IO])
    } yield result

  import MeowMtlBusinessLogic._
  def appLogic[F[_]: Sync: ApplicativeAsk[*[_], Configuration]]: F[ExitCode] =
    for {
      csv <- downloadExcelSheets[F](versionNumber = 1337)
      _   <- storeResult[F](crunchNumbers(csv))
    } yield ExitCode.Success

  override def run(args: List[String]): IO[ExitCode] = withEnv

}

object MeowMtlBusinessLogic {

  def downloadExcelSheets[F[_]: Monad](
      versionNumber: Int
  )(implicit conf: ApplicativeAsk[F, AwsConfiguration], sync: Sync[F]) =
    for {
      configuration <- conf.ask
      _             <- sync.delay(println(s"Downloading csv with conf: ${configuration}"))
    } yield "this, is, csv, data"

  def crunchNumbers(csv: String): Double = csv.count(_ == ',')

  def storeResult[F[_]: Monad](
      crunchedNumbers: Double
  )(implicit conf: ApplicativeAsk[F, DatabaseConfiguration], sync: Sync[F]): F[Unit] =
    for {
      configuration <- conf.ask
      _             <- sync.delay(println(s"Inserting rows with conf: ${configuration}"))
    } yield ()

}
