package com.cdk.budgetbud.repository

import com.cdk.budgetbud.room.CurrencyValue
import io.reactivex.Observable

class ExchangeRateRepository(
    private val remoteDataSource: ExchangeRateContract.RemoteDataSource,
    private val localDataSource: ExchangeRateContract.LocalDataSource
) : ExchangeRateContract.Repository {

    override fun setExchangeRates(rates: List<CurrencyValue>) {
        return localDataSource.setExchangeRates(rates)
    }

    override fun getExchangeRates(apiKey: String, baeCurrencyCode: String): Observable<List<CurrencyValue>> {
        return remoteDataSource.getExchangeRates(,)
    }
}