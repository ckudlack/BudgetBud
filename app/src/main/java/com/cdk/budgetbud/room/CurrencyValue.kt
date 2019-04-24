package com.cdk.budgetbud.room

import androidx.room.Entity

@Entity(tableName = "exchange_rate_table")
class CurrencyValue(
    val name: String,
    val amount: Double
)