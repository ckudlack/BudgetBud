package com.cdk.budgetbud

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val serviceModule = module {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://openexchangerates.org/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    retrofit.create(ExchangeRateService::class.java)
}

val viewModelModule = module {

}

val repositoryModule = module {

}

val dataSourceModule = module {

}

val appModules = listOf(serviceModule, viewModelModule, repositoryModule, dataSourceModule)