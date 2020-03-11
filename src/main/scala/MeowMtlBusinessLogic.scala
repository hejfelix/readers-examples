import cats.Monad
import cats.effect.concurrent.Ref
import cats.effect.{ExitCode, IO, IOApp, Sync}
import cats.mtl.ApplicativeAsk
import cats.implicits._
import com.olegpy.meow.hierarchy._
import com.olegpy.meow.effects._

object MeowMtlBusinessLogicApp extends IOApp {

  val config = Configuration(
    DatabaseConfiguration("jdbc://....."),
    AwsConfiguration("access_key_id", "secret_access_key")
  )

  def withEnv: IO[ExitCode] =
    for {
      ref    <- Ref.of[IO, Configuration](config)
      result <- ref.runAsk(implicit config => appLogic[IO])
    } yield result

  import MtlBusinessLogic._
  def appLogic[F[_]: Sync: ApplicativeAsk[*[_], Configuration]]: F[ExitCode] =
    for {
      csv <- downloadExcelSheets[F](versionNumber = 1337)
      _   <- storeResult[F](crunchNumbers(csv))
    } yield ExitCode.Success

  override def run(args: List[String]): IO[ExitCode] = withEnv

}
