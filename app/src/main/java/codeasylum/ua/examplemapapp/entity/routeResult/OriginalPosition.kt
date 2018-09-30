package codeasylum.ua.examplemapapp.entity.routeResult

import com.google.gson.annotations.SerializedName

data class OriginalPosition(
    @SerializedName("latitude") var latitude: Double = 0.0,
    @SerializedName("longitude") var longitude: Double = 0.0
)