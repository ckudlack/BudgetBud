package com.cdk.budgetbud.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BudgetItemDAO {

    @Insert
    fun addBudgetItem(item: RoomBudgetItem)

    @Query("SELECT * from budget_item_table ORDER BY time DESC")
    fun getBudgetItems(): List<RoomBudgetItem>

    @Query("SELECT * from budget_item_table WHERE name LIKE :id")
    fun getBudgetItem(id: Int): RoomBudgetItem

    @Query("SELECT * from budget_item_table WHERE time BETWEEN :minTime AND :maxTime")
    fun getItemsByDate(minTime: Long, maxTime: Long): List<RoomBudgetItem>
}