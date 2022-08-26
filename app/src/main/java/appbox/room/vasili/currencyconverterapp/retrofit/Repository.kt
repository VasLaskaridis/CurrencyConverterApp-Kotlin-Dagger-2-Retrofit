package appbox.room.vasili.currencyconverterapp.retrofit

import appbox.room.vasili.currencyconverterapp.retrofit.models.APIResponse
import appbox.room.vasili.currencyconverterapp.util.Resource
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: APIService){

    suspend fun getRates(base:String): Resource<APIResponse> {
        return try{
            val response=apiService.getRates(base)
            val data=response.body()
            if(response.isSuccessful && data!=null){
                Resource.Success(data)
            }else{
                Resource.Error(response.message())
            }
        }catch (e:Exception){
            Resource.Error(e.message ?: "Error")
        }
    }
}