package codeasylum.ua.examplemapapp.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import codeasylum.ua.examplemapapp.api.RetrofitManager
import codeasylum.ua.examplemapapp.entity.*
import codeasylum.ua.examplemapapp.entity.geocodeResult.Result
import codeasylum.ua.examplemapapp.repository.*

class SelectStreetViewModel : ViewModel() {

    val geocodeResultLiveData = MutableLiveData<List<Result>>()
    val errorLiveData = MutableLiveData<String>()

    fun getGeocodeResult(address: String, app_id: String, ap_code: String) {
        val formattedAddress = address.replace(" ", "+")
        val query = mutableMapOf(APP_ID_KEY to app_id, APP_CODE_KEY to ap_code,
                COUNTRY_KEY to COUNTRY,
                SEARCH_TEX_KEY to formattedAddress)
        geocodeRepository.getData(geocodeResultCallback, query)

    }

    private val geocodeRepository by lazy {
        GeocodeRepository(RetrofitManager.getClient()!!)
    }


    private val geocodeResultCallback: OnGetDataResultCallback<Result> = object : OnGetDataResultCallback<Result> {

        override fun onSuccess(data: List<Result>) {
            geocodeResultLiveData.postValue(data)
        }

        override fun onError(message: String) {
            errorLiveData.postValue(message)
        }

    }
}