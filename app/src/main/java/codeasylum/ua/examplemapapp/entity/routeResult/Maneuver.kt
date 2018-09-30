package codeasylum.ua.examplemapapp.entity.routeResult

import com.google.gson.annotations.SerializedName

data class Maneuver(
        @SerializedName("position") var position: Position = Position(),
        @SerializedName("id") var id: String = ""
)