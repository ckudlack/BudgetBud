package com.cdk.budgetbud.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.cdk.budgetbud.mvrx.MvRxViewModel
import com.cdk.budgetbud.repository.BudgetItemContract

data class BudgetItem(
    val name: String,
    val cost: Double,
    val time: Long
)

data class BudgetItemState(
    val budgetItems: List<BudgetItem> = emptyList(),
    val getBudgetItemsRequest: Async<List<BudgetItem>> = Uninitialized
) : MvRxState

class BudgetItemViewModel(
    budgetItemState: BudgetItemState,
    private val repository: BudgetItemContract.Repository
) : MvRxViewModel<BudgetItemState>(budgetItemState) {

    init {
        getBudgetItems()
    }

    private fun getBudgetItems() {
        withState { state ->
            if (state.getBudgetItemsRequest is Loading) return@withState

            repository.getBudgetItems()
                .execute {
                    copy(budgetItems = it() ?: emptyList())
                }
        }
    }

    // itemString example: "Spent 20 dollars on dinner"
    fun saveBudgetItem(itemString: String) {
        withState { state ->
            val budgetItem = processItemString(itemString)
            repository.saveBudgetItem(budgetItem)
        }
    }

    // TODO: improve this
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

        return BudgetItem(subject!!, amount!!, System.currentTimeMillis())

//        speech_result.text = "You just spent $amount $currency on $subject"
    }
}