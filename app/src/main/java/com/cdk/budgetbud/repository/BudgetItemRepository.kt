package com.cdk.budgetbud.repository

import com.cdk.budgetbud.viewmodel.BudgetItem
import io.reactivex.Completable
import io.reactivex.Observable

class BudgetItemRepository(private val localDataSource: BudgetItemContract.LocalDataSource,
                           private val remoteDataSource: BudgetItemContract.RemoteDataSource) : BudgetItemContract.Repository {
    override fun getBudgetItems(): Observable<List<BudgetItem>> {
        return localDataSource.getBudgetItems()
    }

    override fun saveBudgetItem(item: BudgetItem): Completable {
        return localDataSource.saveBudgetItem(item).andThen(remoteDataSource.saveBudgetItem(item))
    }
}