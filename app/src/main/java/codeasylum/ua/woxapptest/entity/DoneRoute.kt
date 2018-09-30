package codeasylum.ua.woxapptest.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "done_routes")
data class DoneRoute(@PrimaryKey(autoGenerate = true) var id: Long? = null,
                     @ColumnInfo(name = "base_data") var base_data: String) : Serializable