package codeasylum.ua.woxapptest.api

import codeasylum.ua.woxapptest.entity.geocodeResult.GeocodeResult
import codeasylum.ua.woxapptest.entity.routeResult.RouteResponse
import codeasylum.ua.woxapptest.entity.routeResult.RouteResult
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApi {

    @GET
    fun getAddress(@Url url: String, @QueryMap(encoded = true) queryMap: Map<String, String>): Call<GeocodeResult>

    @GET
    fun getRoutes(@Url url: String, @QueryMap queryMap: Map<String, String>): Call<RouteResult>

}