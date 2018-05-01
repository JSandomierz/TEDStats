package edu.allegroTask.scala

object CurrencyConverter {
  val euroToOthers =
    Map[String,BigDecimal](
      ("EUR", 1.0),
      ("USD", 1.2070),
      ("JPY", 131.95),
      ("BGN", 1.9558),
      ("CZK", 25.471),
      ("DKK", 7.4501),
      ("GBP", 0.87700),
      ("HUF", 312.84),
      ("PLN", 4.2161),
      ("RON", 4.6615),
      ("SEK", 10.5178),
      ("CHF", 1.1960),
      ("ISK", 122.60),
      ("NOK", 9.6590),
      ("HRK", 7.4195),
      ("RUB", 75.4175),
      ("TRY", 4.8882),
      ("AUD", 1.5975),
      ("BRL", 4.1992),
      ("CAD", 1.5549),
      ("CNY", 7.6517),
      ("HKD", 9.4731),
      ("IDR", 16723.29),
      ("ILS", 4.3376),
      ("INR", 80.4645),
      ("KRW", 1291.82),
      ("MXN", 22.6929),
      ("MYR", 4.7231),
      ("NZD", 1.7116),
      ("PHP", 62.392),
      ("SGD", 1.6005),
      ("THB", 38.117),
      ("LTL", 3.45280),
      ("LVL", 0.70280),
      ("MTL", 0.42930),
      ("EEK", 15.64664),
      ("MKD", 61.42474),
      ("TRY", 4.90706),
      ("ZAR", 14.9637))
  def toEuro(currencySymbol: String, ammount: BigDecimal):BigDecimal={
    val euroToCurrent: BigDecimal = euroToOthers.getOrElse(currencySymbol,0)
    if(euroToCurrent != 0){
      val result: BigDecimal = ammount / euroToCurrent
      return result
    }
    return 0
  }
}
