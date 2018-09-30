package codeasylum.ua.examplemapapp.entity.geocodeResult

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("Relevance") val relevance: Double = 0.0,
    @SerializedName("MatchLevel") val matchLevel: String = "",
    @SerializedName("MatchType") val matchType: String = "",
    @SerializedName("Location") val location: Location = Location()
)