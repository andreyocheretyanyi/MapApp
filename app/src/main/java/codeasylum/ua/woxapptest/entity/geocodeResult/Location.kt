package codeasylum.ua.woxapptest.entity.geocodeResult

import com.google.gson.annotations.SerializedName

data class Location(
        @SerializedName("LocationId") val locationId: String = "",
        @SerializedName("LocationType") val locationType: String = "",
        @SerializedName("DisplayPosition") val displayPosition: DisplayPosition = DisplayPosition(),
        @SerializedName("Address") val address: Address = Address()
)