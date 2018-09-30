package codeasylum.ua.examplemapapp.repository

import codeasylum.ua.examplemapapp.api.RetrofitApi
import codeasylum.ua.examplemapapp.entity.geocodeResult.GeocodeResult
import codeasylum.ua.examplemapapp.entity.geocodeResult.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




private const val URL_GEOCODE = "https://geocoder.api.here.com/6.2/geocode.json?addressattributes=cit,str,hnr"


class GeocodeRepository(private val api: RetrofitApi) : Repository<Result> {

    override fun insertAll(dataList: List<Result>) {

    }

    override fun insertData(item: Result, onDataInsertedCallback: OnDataInsertedCallback) {

    }

    override fun deleteData(item: Result) {

    }

    override fun deleteData(id: Int) {

    }


    private var queryMap: MutableMap<String, String> = mutableMapOf()

    override fun getData(onGetDataResultCallback: OnGetDataResultCallback<Result>, queryMap: MutableMap<String, String>) {
        this.queryMap = queryMap
        getData(onGetDataResultCallback)
    }

    override fun getData(onGetDataResultCallback: OnGetDataResultCallback<Result>) {
        api.getAddress(URL_GEOCODE, queryMap).enqueue(object : Callback<GeocodeResult> {

            override fun onResponse(call: Call<GeocodeResult>, response: Response<GeocodeResult>) {
                response.body()?.response?.view?.takeIf {
                    it.isNotEmpty()
                }?.get(0)?.result?.apply {
                    if (this.isNotEmpty()) {
                        onGetDataResultCallback.onSuccess(this)
                        return
                    }
                }
                onGetDataResultCallback.onSuccess(listOf())

            }

            override fun onFailure(call: Call<GeocodeResult>, t: Throwable) {
                onGetDataResultCallback.onError(t.message!!)
            }

        })

    }


}