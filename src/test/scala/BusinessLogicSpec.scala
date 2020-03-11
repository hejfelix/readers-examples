class BusinessLogicSpec extends org.specs2.mutable.Specification {

  "BusinessLogic" >> {

    "download csv" >> {
      val conf = Configuration(
        DatabaseConfiguration("jdbc://....."),
        AwsConfiguration("access_key_id", "secret_access_key")
      )
      val businessLogic = new BusinessLogic(conf)
      businessLogic.downloadExcelSheets(42) must_== "this, is, csv, data"
    }

    "crunch numbers" >> {
      val conf = Configuration(
        DatabaseConfiguration("jdbc://....."),
        AwsConfiguration("access_key_id", "secret_access_key")
      )
      val businessLogic = new BusinessLogic(conf)
      businessLogic.crunchNumbers("a,b,c,d") must_== 3
    }

    "store result" >> {
      val conf = Configuration(
        DatabaseConfiguration("jdbc://....."),
        AwsConfiguration("access_key_id", "secret_access_key")
      )
      val businessLogic = new BusinessLogic(conf)
      businessLogic.storeResult(3) must_== (())
    }

  }

}
