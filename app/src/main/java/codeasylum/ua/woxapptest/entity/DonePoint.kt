package codeasylum.ua.woxapptest.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "done_point", foreignKeys = [ForeignKey(entity = DoneRoute::class, parentColumns = ["id"], childColumns = ["route_id"], onDelete = ForeignKey.CASCADE)])
class DonePoint(
        @ColumnInfo(name = "route_id") var route_id: Long? = null,
                var address: String = "",
                var lat: Double = 0.0,
                var lng: Double = 0.0) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
