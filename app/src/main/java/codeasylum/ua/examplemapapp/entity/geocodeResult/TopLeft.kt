package codeasylum.ua.examplemapapp.entity.geocodeResult

import com.google.gson.annotations.SerializedName

data class TopLeft(
    @SerializedName("Latitude") val latitude: Double = 0.0,
    @SerializedName("Longitude") val longitude: Double = 0.0
)