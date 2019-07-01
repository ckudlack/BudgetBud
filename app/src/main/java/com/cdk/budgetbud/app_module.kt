package com.cdk.budgetbud

import com.cdk.budgetbud.datasource.BudgetItemLocalDataSource
import com.cdk.budgetbud.datasource.BudgetItemRemoteDataSource
import com.cdk.budgetbud.datasource.ExchangeRateLocalDataSource
import com.cdk.budgetbud.datasource.ExchangeRateRemoteDataSource
import com.cdk.budgetbud.network.ExchangeRateService
import com.cdk.budgetbud.repository.BudgetItemContract
import com.cdk.budgetbud.repository.BudgetItemRepository
import com.cdk.budgetbud.repository.ExchangeRateContract
import com.cdk.budgetbud.repository.ExchangeRateRepository
import com.cdk.budgetbud.room.BudgetBudDatabase
import com.cdk.budgetbud.viewmodel.BudgetItemViewModel
import com.cdk.budgetbud.viewmodel.ExchangeRateViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val serviceModule = module {
    single {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        retrofit.create(ExchangeRateService::class.java)
    }
}

val viewModelModule = module {
    viewModel { ExchangeRateViewModel(get(), get()) }
    viewModel { BudgetItemViewModel(get(), get()) }
}

val repositoryModule = module {
    single<ExchangeRateContract.Repository> { ExchangeRateRepository(get(), get()) }
    single<BudgetItemContract.Repository> { BudgetItemRepository(get(), get()) }
}

val dataSourceModule = module {
    single<ExchangeRateContract.RemoteDataSource> { ExchangeRateRemoteDataSource(get()) }
    single<ExchangeRateContract.LocalDataSource> { ExchangeRateLocalDataSource(get()) }

    single<BudgetItemContract.LocalDataSource> { BudgetItemLocalDataSource(get()) }
    single<BudgetItemContract.RemoteDataSource> { BudgetItemRemoteDataSource() }
}

val daoModule = module {
    single { BudgetBudDatabase.getDatabase(androidApplication()).currencyDao() }
    single { BudgetBudDatabase.getDatabase(androidApplication()).budgetItemDao() }
}

val appModules = listOf(serviceModule, viewModelModule, repositoryModule, dataSourceModule, daoModule)