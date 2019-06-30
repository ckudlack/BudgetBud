package com.cdk.budgetbud.viewmodel

import com.airbnb.mvrx.MvRxState
import com.cdk.budgetbud.mvrx.MvRxViewModel
import com.cdk.budgetbud.repository.BudgetItemContract

data class BudgetItem(
    val name: String,
    val cost: Double
)

data class BudgetItemState(
    val budgetItems: List<BudgetItem> = emptyList()
) : MvRxState

class BudgetItemViewModel(
    budgetItemState: BudgetItemState,
    private val repository: BudgetItemContract.Repository
) : MvRxViewModel<BudgetItemState>(budgetItemState) {

    // itemString example: "Spent 20 dollars on dinner"
    fun saveBudgetItem(itemString: String) {
        withState { state ->
            val budgetItem = processItemString(itemString)
            repository.saveBudgetItem(budgetItem)
        }
    }

    private fun processItemString(itemString: String): BudgetItem {
        val words = itemString.split(' ')
        var amount: Double? = null
        var currency: String? = null
        var article: String? = null
        var subject: String? = null

        // "Spent ten thousand pesos on dinner"
        // "[Trigger] [amount] [subject]"
        if (words.first().toLowerCase() == "spent" || words.first().toLowerCase() == "payed") {

            words.forEachIndexed { index, word ->
                when (index) {
                    1 -> word.toDoubleOrNull()?.let { amount = it }
                    2 -> currency = word
                    3 -> article = word
                    4 -> subject = word
                }
            }
        }

        return BudgetItem(subject!!, amount!!)

//        speech_result.text = "You just spent $amount $currency on $subject"
    }
}