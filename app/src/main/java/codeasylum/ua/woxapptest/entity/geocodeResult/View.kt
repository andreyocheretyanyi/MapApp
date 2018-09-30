package codeasylum.ua.woxapptest.entity.geocodeResult

import com.google.gson.annotations.SerializedName

data class View(
    @SerializedName("_type") val type: String = "",
    @SerializedName("ViewId") val viewId: Double = 0.0,
    @SerializedName("Result") val result: List<Result> = listOf()
)