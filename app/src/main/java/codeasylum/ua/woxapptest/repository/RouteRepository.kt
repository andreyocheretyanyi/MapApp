package codeasylum.ua.woxapptest.repository

import codeasylum.ua.woxapptest.api.RetrofitApi
import codeasylum.ua.woxapptest.entity.routeResult.RouteResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val URL_ROUTE = "https://route.api.here.com/routing/7.2/calculateroute.json?&mode=fastest;car;&routeattributes=shape"

class RouteRepository(private val api: RetrofitApi) : Repository<String> {

    override fun insertAll(dataList: List<String>) {

    }

    private var queryMap: MutableMap<String, String> = mutableMapOf()

    override fun getData(onGetDataResultCallback: OnGetDataResultCallback<String>) {
        api.getRoutes(URL_ROUTE, queryMap).enqueue(object : Callback<RouteResult> {

            override fun onResponse(call: Call<RouteResult>, response: Response<RouteResult>) {
                response.body()?.apply {
                    routeResponse
                            .route
                            .takeIf {
                                it.isNotEmpty()
                            }?.get(0)?.shape?.apply {
                        onGetDataResultCallback.onSuccess(this)
                        return
                    }
                }
                onGetDataResultCallback.onError("Something wrong")

            }

            override fun onFailure(call: Call<RouteResult>, t: Throwable) {
                onGetDataResultCallback.onError(t.message!!)
            }

        }
        )
    }

    override fun getData(onGetDataResultCallback: OnGetDataResultCallback<String>, queryMap: MutableMap<String, String>) {
        this.queryMap = queryMap
        getData(onGetDataResultCallback)
    }


    override fun insertData(item: String, onDataInsertedCallback: OnDataInsertedCallback) {

    }

    override fun deleteData(item: String) {

    }

    override fun deleteData(id: Int) {

    }

}