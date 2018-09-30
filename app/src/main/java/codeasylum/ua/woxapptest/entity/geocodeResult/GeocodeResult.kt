package codeasylum.ua.woxapptest.entity.geocodeResult

import com.google.gson.annotations.SerializedName

data class GeocodeResult(
    @SerializedName("Response") val response: Response = Response()
)