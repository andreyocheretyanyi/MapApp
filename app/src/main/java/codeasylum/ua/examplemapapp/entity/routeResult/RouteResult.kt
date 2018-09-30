package codeasylum.ua.examplemapapp.entity.routeResult

import com.google.gson.annotations.SerializedName

data class RouteResult(
    @SerializedName("response") var routeResponse: RouteResponse = RouteResponse()
)