package com.cdk.budgetbud

import com.cdk.budgetbud.response.AllRatesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateService {

    @GET("latest.json")
    fun getAllExchangeRates(
        @Query("app_id") appId: String,
        @Query("base") baseCurrencyCode: String
    ): Observable<AllRatesResponse>
}