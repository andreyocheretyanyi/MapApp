package codeasylum.ua.examplemapapp.entity.geocodeResult

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("Label") val label: String = "",
    @SerializedName("Country") val country: String = "",
    @SerializedName("State") val state: String = "",
    @SerializedName("County") val county: String = "",
    @SerializedName("City") val city: String = "",
    @SerializedName("District") val district: String = "",
    @SerializedName("Street") val street: String = "",
    @SerializedName("HouseNumber") val houseNumber: String = "",
    @SerializedName("PostalCode") val postalCode: String = ""
)