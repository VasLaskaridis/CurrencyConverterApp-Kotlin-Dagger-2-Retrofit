package appbox.room.vasili.currencyconverterapp.retrofit.models

data class APIResponse(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)