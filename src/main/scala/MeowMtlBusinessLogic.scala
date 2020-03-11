import cats.data.ReaderT
import cats.effect.{ExitCode, IO, IOApp, Sync}
import cats.implicits._
import cats.mtl.ApplicativeAsk
import cats.mtl.implicits._
import com.olegpy.meow.hierarchy._

object MeowMtlBusinessLogicApp extends IOApp {

  val config = Configuration(
    DatabaseConfiguration("jdbc://....."),
    AwsConfiguration("access_key_id", "secret_access_key")
  )

  import MtlBusinessLogic._
  def program[F[_]: Sync: ApplicativeAsk[*[_], Configuration]]: F[ExitCode] =
    for {
      csv <- downloadExcelSheets[F](versionNumber = 1337)
      _   <- storeResult[F](crunchNumbers(csv))
    } yield ExitCode.Success

  override def run(args: List[String]): IO[ExitCode] = program[ReaderT[IO, Configuration, *]].run(config)

}
