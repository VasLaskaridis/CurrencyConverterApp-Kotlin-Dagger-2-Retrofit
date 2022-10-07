package appbox.room.vasili.currencyconverterapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import appbox.room.vasili.currencyconverterapp.retrofit.Repository
import appbox.room.vasili.currencyconverterapp.retrofit.models.Rates
import appbox.room.vasili.currencyconverterapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: Repository,
    private val currencyList: Array<String>
) : ViewModel() {


    sealed class StateOfData {
        class Success(val result: ArrayList<Double>) : StateOfData()
        class Error(val errorText: String) : StateOfData()
        object Loading : StateOfData()
        object Empty : StateOfData()
    }

    private var  convertedCurrency:ArrayList<Double> = ArrayList()

    fun getConvertedCurrency():ArrayList<Double>{
        return convertedCurrency
    }

    private val conversion = MutableStateFlow<StateOfData>(StateOfData.Empty)

    fun conversionObserver(): MutableStateFlow<StateOfData> {
        return conversion
    }

    fun convert(
        amountString: String,
        startCurrency: String
    ) {
        val amount = amountString.toFloatOrNull()
        if (amount == null) {
            conversion.value = StateOfData.Error("Please insert a valid amount")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            conversion.value = StateOfData.Loading
            val response = repository.getRates(startCurrency)
            var error = false
            when (response) {
                is Resource.Error -> {
                    conversion.value = StateOfData.Error(response.message!!)
                }
                is Resource.Success -> {
                    val listOfRates = response.data!!.rates
                    convertedCurrency.clear()
                    for (i in currencyList) {
                        val rate = getRate(i, listOfRates)
                        if (rate == null) {
                            error = true
                            break
                        } else {
                            convertedCurrency.add((amount * rate * 100 / 100))
                        }
                    }
                    if (error){
                        conversion.value = StateOfData.Error("An unexpected error occurred")

                    }
                    else{
                        conversion.value = StateOfData.Success(convertedCurrency)

                    }
                }
            }
        }
    }

    private fun getRate(currency: String, rates: Rates) = when (currency) {
        "CAD" -> rates.CAD
        "EUR" -> rates.EUR
        "GBP" -> rates.GBP
        "DKK" -> rates.DKK
        "AUD" -> rates.AUD
        "SEK" -> rates.SEK
        "RUB" -> rates.RUB
        "JPY" -> rates.JPY
        "CHF" -> rates.CHF
        "NOK" -> rates.NOK
        "USD" -> rates.USD
        "TRY" -> rates.TRY

        else -> null
    }

}