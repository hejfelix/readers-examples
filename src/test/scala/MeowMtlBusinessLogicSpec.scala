import cats.effect.IO
import cats.effect.concurrent.Ref
import com.olegpy.meow.effects._
import com.olegpy.meow.hierarchy._

class MeowMtlBusinessLogicSpec extends org.specs2.mutable.Specification {

  "MeowMtlBusinessLogic" >> {

    "download csv" >> {

      val conf = AwsConfiguration("access_key_id", "secret_access_key")

      (for {
        ref <- Ref.of[IO, AwsConfiguration](conf)
        result <- ref.runAsk { implicit ask =>
          MeowMtlBusinessLogic.downloadExcelSheets[IO](42)
        }
      } yield result).unsafeRunSync must_== "this, is, csv, data"

    }

    "crunch numbers" >> {
      MeowMtlBusinessLogic.crunchNumbers("a,b,c,d") must_== 3
    }

    "store result" >> {

      val conf = DatabaseConfiguration("jdbc://.....")

      (for {
        ref <- Ref.of[IO, DatabaseConfiguration](conf)
        result <- ref.runAsk { implicit ask =>
          MeowMtlBusinessLogic.storeResult[IO](3)
        }
      } yield result).unsafeRunSync must_== (())
    }

  }

}
