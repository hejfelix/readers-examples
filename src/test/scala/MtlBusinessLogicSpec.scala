import cats.effect.IO
import cats.effect.concurrent.Ref
import cats.data.ReaderT
import cats.mtl.implicits._

class MtlBusinessLogicSpec extends org.specs2.mutable.Specification {

  "MeowMtlBusinessLogic" >> {

    "download csv" >> {
      val program = MtlBusinessLogic.downloadExcelSheets[ReaderT[IO, AwsConfiguration, *]](42)
      val config = AwsConfiguration("access_key_id", "secret_access_key")

      program.run(config).unsafeRunSync must_== "this, is, csv, data"
    }

    "crunch numbers" >> {
      MtlBusinessLogic.crunchNumbers("a,b,c,d") must_== 3
    }

    "store result" >> {
      val program = MtlBusinessLogic.storeResult[ReaderT[IO, DatabaseConfiguration, *]](3)
      val config  = DatabaseConfiguration("jdbc://.....")

      program.run(config).unsafeRunSync must_== (())
    }
  }
}
