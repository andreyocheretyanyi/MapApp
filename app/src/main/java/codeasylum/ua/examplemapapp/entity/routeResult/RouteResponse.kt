package codeasylum.ua.examplemapapp.entity.routeResult

import com.google.gson.annotations.SerializedName

data class RouteResponse(
    @SerializedName("route") var route: List<Route> = listOf()
)