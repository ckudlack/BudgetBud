package com.cdk.budgetbud

import com.airbnb.mvrx.activityViewModel
import com.cdk.budgetbud.mvrx.simpleController
import com.cdk.budgetbud.viewmodel.ExchangeRateViewModel

class HomeFragment : BaseFragment() {

    private val exchangeRateViewModel: ExchangeRateViewModel by activityViewModel()

    override fun epoxyController() = simpleController(exchangeRateViewModel) { exchangeRateState ->

    }
}