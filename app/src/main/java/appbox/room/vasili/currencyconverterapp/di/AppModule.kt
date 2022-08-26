package appbox.room.vasili.currencyconverterapp.di

import appbox.room.vasili.currencyconverterapp.retrofit.APIService
import appbox.room.vasili.currencyconverterapp.retrofit.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCurrencyList(): Array<String> {
        return arrayOf("AUD",
            "CAD",
            "CHF",
            "DKK",
            "EUR",
            "GBP",
            "JPY",
            "NOK",
            "RUB",
            "SEK",
            "TRY",
            "USD" )
    }

    @Provides
    @Singleton
    fun provideAPIService( ):APIService{
        return Retrofit.Builder()
            .baseUrl("https://api.apilayer.com/exchangerates_data/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    @Singleton
    @Provides
    fun provideMainRepository(api: APIService): Repository {
        return Repository(api)
    }
}