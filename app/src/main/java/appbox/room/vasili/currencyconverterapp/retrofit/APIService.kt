package appbox.room.vasili.currencyconverterapp.retrofit

import appbox.room.vasili.currencyconverterapp.retrofit.models.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService  {
    @Headers("apikey: lxyAzJA8Ed1okfldwpSGRo7nPP8UiILs")
    @GET("latest?symbols=AUD%2CCAD%2CCHF%2CDKK%2CEUR%2CGBP%2CJPY%2CNOK%2CRUB%2CSEK%2CTRY%2CUSD")
    suspend fun getRates(
        @Query("base") base: String
    ): Response<APIResponse>
}