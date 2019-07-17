package com.cdk.budgetbud.repository

import com.cdk.budgetbud.viewmodel.BudgetItem
import io.reactivex.Completable
import io.reactivex.Observable
import java.time.LocalDate

interface BudgetItemContract {

    interface Repository {
        fun saveBudgetItem(item: BudgetItem): Completable
        fun getBudgetItems(): Observable<List<BudgetItem>>
        fun getBudgetItemsForDate(date: LocalDate): Observable<List<BudgetItem>>
    }

    interface LocalDataSource {
        fun saveBudgetItem(item: BudgetItem): Completable
        fun getBudgetItems(): Observable<List<BudgetItem>>
        fun getBudgetItemsForDate(date: LocalDate): Observable<List<BudgetItem>>
    }

    interface RemoteDataSource {
        fun saveBudgetItem(item: BudgetItem): Completable
        fun getBudgetItems(): Observable<List<BudgetItem>>
    }
}