package com.cdk.budgetbud.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface BudgetBudDAO {

//    @Query("SELECT * from table")
    fun convertUnits()
}