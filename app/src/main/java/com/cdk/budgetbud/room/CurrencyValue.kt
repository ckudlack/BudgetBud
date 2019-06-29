package com.cdk.budgetbud.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rate_table")
class CurrencyValue(
    @PrimaryKey val name: String,
    val amount: Double
)