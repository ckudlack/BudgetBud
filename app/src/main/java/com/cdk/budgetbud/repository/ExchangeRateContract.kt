package com.cdk.budgetbud.repository

import com.cdk.budgetbud.room.CurrencyValue
import io.reactivex.Observable

interface ExchangeRateContract {
    interface Repository {
        fun setExchangeRates(rates: List<CurrencyValue>)
        fun getExchangeRates(apiKey: String, baseCurrencyCode: String): Observable<List<CurrencyValue>>
    }

    interface LocalDataSource {
        fun setExchangeRates(rates: List<CurrencyValue>)
        fun getExchangeRates(): Observable<List<CurrencyValue>>
    }

    interface RemoteDataSource {
        fun getExchangeRates(apiKey: String, baseCurrencyCode: String): Observable<List<CurrencyValue>>
    }
}