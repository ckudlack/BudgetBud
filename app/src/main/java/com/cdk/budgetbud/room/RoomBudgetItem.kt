package com.cdk.budgetbud.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_item_table")
class RoomBudgetItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val cost: Double,
    val time: Long
)