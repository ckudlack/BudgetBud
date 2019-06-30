package com.cdk.budgetbud.repository

import com.cdk.budgetbud.viewmodel.BudgetItem
import io.reactivex.Completable

interface BudgetItemContract {

    interface Repository {
        fun saveBudgetItem(item: BudgetItem): Completable
    }
}