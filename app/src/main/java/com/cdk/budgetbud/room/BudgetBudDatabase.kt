package com.cdk.budgetbud.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrencyValue::class, RoomBudgetItem::class], version = 1)
public abstract class BudgetBudDatabase : RoomDatabase() {
    abstract fun currencyDao(): ExchangeRateDAO
    abstract fun budgetItemDao(): BudgetItemDAO

    companion object {
        @Volatile
        private var INSTANCE: BudgetBudDatabase? = null

        fun getDatabase(context: Context): BudgetBudDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BudgetBudDatabase::class.java,
                    "budget_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}