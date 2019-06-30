package com.cdk.budgetbud.datasource

import com.cdk.budgetbud.repository.BudgetItemContract
import com.cdk.budgetbud.viewmodel.BudgetItem
import io.reactivex.Completable

class BudgetItemLocalDataSource : BudgetItemContract.LocalDataSource {
    override fun saveBudgetItem(item: BudgetItem): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}