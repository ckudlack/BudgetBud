package com.cdk.budgetbud.response

data class AllRatesResponse(
    val disclaimer: String?,
    val license: String?,
    val timestamp: Long = 0,
    val base: String?,
    val rates: Map<String, Double>? = null
)

/**
*
* {
   "disclaimer":"https://openexchangerates.org/terms/",
   "license":"https://openexchangerates.org/license/",
   "timestamp":1449877801,
   "base":"USD",
   "rates":{
      "AED":3.672538,
      "AFN":66.809999,
      "ALL":125.716501,
      "AMD":484.902502,
      "ANG":1.788575,
      "AOA":135.295998,
      "ARS":9.750101,
      "AUD":1.390866
   }
}
*
* */