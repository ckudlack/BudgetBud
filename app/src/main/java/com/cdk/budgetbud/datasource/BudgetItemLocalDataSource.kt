package com.cdk.budgetbud.datasource

import com.cdk.budgetbud.repository.BudgetItemContract
import com.cdk.budgetbud.room.BudgetItemDAO
import com.cdk.budgetbud.room.RoomBudgetItem
import com.cdk.budgetbud.viewmodel.BudgetItem
import io.reactivex.Completable
import io.reactivex.Observable

class BudgetItemLocalDataSource(private val budgetItemDAO: BudgetItemDAO) : BudgetItemContract.LocalDataSource {

    override fun getBudgetItems(): Observable<List<BudgetItem>> {
        return Observable.fromIterable(budgetItemDAO.getBudgetItems())
            .map { BudgetItem(it.name, it.cost, it.time) }
            .toList()
            .toObservable()
    }

    override fun saveBudgetItem(item: BudgetItem): Completable {
        budgetItemDAO.addBudgetItem(
            RoomBudgetItem(
                name = item.name,
                cost = item.cost,
                time = item.time
            )
        )
        return Completable.complete()
    }
}