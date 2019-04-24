package com.cdk.budgetbud.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExchangeRateDAO {

    @Insert
    fun setExchangeRates(rates: List<CurrencyValue>)

    @Query("SELECT * from exchange_rate_table ORDER BY name ASC")
    fun getExchangeRates(): List<CurrencyValue>

    @Query("SELECT * from exchange_rate_table WHERE name LIKE :currencyName")
    fun getExchangeRate(currencyName: String): CurrencyValue
}