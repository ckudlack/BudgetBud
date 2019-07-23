package com.cdk.budgetbud.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.cdk.budgetbud.repository.BudgetItemContract
import com.cdk.budgetbud.room.BudgetItemDAO
import com.cdk.budgetbud.room.RoomBudgetItem
import com.cdk.budgetbud.viewmodel.BudgetItem
import io.reactivex.Completable
import io.reactivex.Observable
import java.time.LocalDate
import java.time.ZoneId

class BudgetItemLocalDataSource(private val budgetItemDAO: BudgetItemDAO) : BudgetItemContract.LocalDataSource {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getBudgetItemsForDate(date: LocalDate): Observable<List<BudgetItem>> {
        val startOfDay = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return Observable.fromIterable(
            budgetItemDAO.getItemsByDate(
                startOfDay,
                startOfDay + ONE_DAY
            )
        )
            .map { BudgetItem(it.id, it.name, it.cost, it.time) }
            .toList()
            .toObservable()
    }

    override fun getBudgetItems(): Observable<List<BudgetItem>> {
        return Observable.fromIterable(budgetItemDAO.getBudgetItems())
            .map { BudgetItem(it.id, it.name, it.cost, it.time) }
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

    companion object {
        const val ONE_DAY = 86400000
    }
}