package codeasylum.ua.woxapptest.entity.geocodeResult

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("View") val view: List<View> = listOf()
)