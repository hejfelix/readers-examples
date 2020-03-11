class ReaderBusinessLogicSpec extends org.specs2.mutable.Specification {

  "ReaderBusinessLogic" >> {

    "download csv" >> {
      val conf = Configuration(
        DatabaseConfiguration("jdbc://....."),
        AwsConfiguration("access_key_id", "secret_access_key")
      )

      ReaderBusinessLogic.downloadExcelSheets(42).run(conf).unsafeRunSync() must_== "this, is, csv, data"
    }

    "crunch numbers" >> {
      ReaderBusinessLogic.crunchNumbers("a,b,c,d") must_== 3
    }

    "store result" >> {
      val conf = Configuration(
        DatabaseConfiguration("jdbc://....."),
        AwsConfiguration("access_key_id", "secret_access_key")
      )

      ReaderBusinessLogic.storeResult(3).run(conf).unsafeRunSync() must_== (())
    }

  }

}
