package com.cdk.budgetbud.viewmodel

import com.airbnb.mvrx.*
import com.cdk.budgetbud.BudgetItemMapper
import com.cdk.budgetbud.mvrx.MvRxViewModel
import com.cdk.budgetbud.repository.BudgetItemContract
import org.koin.android.ext.android.inject
import java.text.DecimalFormat
import java.text.ParseException
import java.time.LocalDate
import java.util.*

enum class BudgetViewType {
    ITEM,
    HEADER
}

data class BudgetViewItem(
    val value: String,
    val type: BudgetViewType
)

data class BudgetItem(
    val name: String,
    val cost: Double,
    val time: Long
)

data class BudgetItemState(
    val budgetItems: List<BudgetViewItem> = emptyList(),
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
                    copy(
                        getBudgetItemsRequest = it,
                        budgetItems = BudgetItemMapper.toBudgetViewItemList(it() ?: emptyList())
                    )
                }
        }
    }

    // itemString example: "Spent 20 dollars on dinner"
    fun saveBudgetItem(itemString: String) {
        withState { state ->
            val budgetItem = processItemString(itemString)
            repository.saveBudgetItem(budgetItem)
                .andThen(repository.getBudgetItems()).execute {
                    copy(
                        getBudgetItemsRequest = it,
                        budgetItems = BudgetItemMapper.toBudgetViewItemList(it() ?: emptyList())
                    )
                }
        }
    }

    fun getBudgetItemsForDate(date: LocalDate) {
        withState { state ->
            repository.getBudgetItemsForDate(date)
                .execute {
                    copy(
                        getBudgetItemsRequest = it,
                        budgetItems = BudgetItemMapper.toBudgetViewItemList(it() ?: emptyList())
                    )
                }
        }
    }

    // TODO: improve this -  needs to be more flexible
    private fun processItemString(itemString: String): BudgetItem {
        val words = itemString.split(' ')
        var amount: Double? = null
        var currency: String? = null
        var article: String? = null
        var subject: String? = null

        // "Spent ten thousand pesos on dinner"
        // "spent $40 on food"
        // "spent 40 on food"
        // "[Trigger] [amount] [subject]"
        if (words.first().toLowerCase() == "spent" || words.first().toLowerCase() == "payed") {

            words.forEachIndexed { index, word ->
                when (index) {
                    1 -> {
                        amount = try {
                            val cost = DecimalFormat.getCurrencyInstance(Locale.getDefault()).parse(word)
                            cost.toDouble()
                        } catch (ex: ParseException) {
                            word.toDouble()
                        }
                    }
                    2 -> currency = word
                    3 -> subject = word
//                    4 -> subject = word
                }
            }
        } else {

        }

        return BudgetItem(subject!!, amount!!, System.currentTimeMillis())
    }

    companion object : MvRxViewModelFactory<BudgetItemViewModel, BudgetItemState> {
        override fun create(viewModelContext: ViewModelContext, state: BudgetItemState): BudgetItemViewModel? {
            val repository: BudgetItemContract.Repository by viewModelContext.activity.inject()
            return BudgetItemViewModel(state, repository)
        }
    }
}