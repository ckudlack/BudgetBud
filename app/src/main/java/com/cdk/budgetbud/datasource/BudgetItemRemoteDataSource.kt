package com.cdk.budgetbud.datasource

import com.cdk.budgetbud.repository.BudgetItemContract
import com.cdk.budgetbud.viewmodel.BudgetItem
import io.reactivex.Completable
import io.reactivex.Observable

class BudgetItemRemoteDataSource : BudgetItemContract.RemoteDataSource {

    override fun getBudgetItems(): Observable<List<BudgetItem>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveBudgetItem(item: BudgetItem): Completable {
        return Completable.complete()
    }
}