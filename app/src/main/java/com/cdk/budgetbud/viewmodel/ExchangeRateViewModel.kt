package com.cdk.budgetbud.viewmodel

import com.airbnb.mvrx.*
import com.cdk.budgetbud.mvrx.MvRxViewModel
import com.cdk.budgetbud.repository.ExchangeRateContract
import com.cdk.budgetbud.room.CurrencyValue
import org.koin.android.ext.android.inject

data class ExchangeRateState(
    val conversionRate: Double,
    val exchangeRates: List<CurrencyValue>,
    val exchangeRatesRequest: Async<List<CurrencyValue>>
) : MvRxState

class ExchangeRateViewModel(
    exchangeRateState: ExchangeRateState,
    private val repository: ExchangeRateContract.Repository
) : MvRxViewModel<ExchangeRateState>(exchangeRateState) {

    private fun getExchangeRates(apiKey: String, baseCurrencyCode: String) {
        withState { state ->
            if (state.exchangeRatesRequest is Loading) return@withState

            repository.getExchangeRates(apiKey, baseCurrencyCode)
                .execute {
                    copy(
                        exchangeRatesRequest = it,
                        exchangeRates = it() ?: emptyList()
                    )
                }
        }
    }

    companion object : MvRxViewModelFactory<ExchangeRateViewModel, ExchangeRateState> {
        override fun create(viewModelContext: ViewModelContext, state: ExchangeRateState): ExchangeRateViewModel? {
            val repository: ExchangeRateContract.Repository by viewModelContext.activity.inject()
            return ExchangeRateViewModel(state, repository)
        }
    }
}