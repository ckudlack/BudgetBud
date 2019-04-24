package com.cdk.budgetbud.datasource

import com.cdk.budgetbud.repository.ExchangeRateContract
import com.cdk.budgetbud.room.CurrencyValue
import com.cdk.budgetbud.room.ExchangeRateDAO
import io.reactivex.Observable

class ExchangeRateLocalDataSource(private val exchangeRateDAO: ExchangeRateDAO) : ExchangeRateContract.LocalDataSource {

    override fun setExchangeRates(rates: List<CurrencyValue>) {
        exchangeRateDAO.setExchangeRates(rates)
    }

    override fun getExchangeRates(): Observable<List<CurrencyValue>> {
        return Observable.just(exchangeRateDAO.getExchangeRates())
    }
}