package com.cdk.budgetbud.repository

import com.cdk.budgetbud.viewmodel.BudgetItem
import io.reactivex.Completable
import io.reactivex.Observable
import java.time.LocalDate

class BudgetItemRepository(
    private val localDataSource: BudgetItemContract.LocalDataSource,
    private val remoteDataSource: BudgetItemContract.RemoteDataSource
) : BudgetItemContract.Repository {

    override fun getBudgetItemsForDate(date: LocalDate): Observable<List<BudgetItem>> =
        localDataSource.getBudgetItemsForDate(date)

    override fun getBudgetItems(): Observable<List<BudgetItem>> = localDataSource.getBudgetItems()

    override fun saveBudgetItem(item: BudgetItem): Completable =
        localDataSource.saveBudgetItem(item).andThen(remoteDataSource.saveBudgetItem(item))
}