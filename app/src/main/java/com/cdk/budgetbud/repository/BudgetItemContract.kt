package com.cdk.budgetbud.repository

import com.cdk.budgetbud.viewmodel.BudgetItem
import io.reactivex.Completable
import io.reactivex.Observable

interface BudgetItemContract {

    interface Repository {
        fun saveBudgetItem(item: BudgetItem): Completable
        fun getBudgetItems(): Observable<List<BudgetItem>>
    }

    interface LocalDataSource {
        fun saveBudgetItem(item: BudgetItem): Completable
        fun getBudgetItems(): Observable<List<BudgetItem>>
    }

    interface RemoteDataSource {
        fun saveBudgetItem(item: BudgetItem): Completable
        fun getBudgetItems(): Observable<List<BudgetItem>>
    }
}