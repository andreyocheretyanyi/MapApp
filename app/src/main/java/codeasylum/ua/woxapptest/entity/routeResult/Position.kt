package codeasylum.ua.woxapptest.entity.routeResult

import com.google.gson.annotations.SerializedName

data class Position(
        @SerializedName("latitude") var latitude: Double = 0.0,
        @SerializedName("longitude") var longitude: Double = 0.0
)