package codeasylum.ua.woxapptest.entity.routeResult

import com.google.gson.annotations.SerializedName

data class Route(
    @SerializedName("shape") var shape : List<String> = listOf())