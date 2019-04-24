package com.cdk.budgetbud.datasource

import com.cdk.budgetbud.network.ExchangeRateService
import com.cdk.budgetbud.repository.ExchangeRateContract
import com.cdk.budgetbud.room.CurrencyValue
import io.reactivex.Observable

class ExchangeRateRemoteDataSource(private val exchangeRateService: ExchangeRateService) :
    ExchangeRateContract.RemoteDataSource {

    override fun getExchangeRates(apiKey: String, baseCurrencyCode: String): Observable<List<CurrencyValue>> {
        return exchangeRateService.getAllExchangeRates(apiKey, baseCurrencyCode).map { ratesResponse ->
            val rates = mutableListOf<CurrencyValue>()
            ratesResponse.rates?.forEach { rates.add(CurrencyValue(it.key, it.value)) }
            return@map rates
        }
    }
}