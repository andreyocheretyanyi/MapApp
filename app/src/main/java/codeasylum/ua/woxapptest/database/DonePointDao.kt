package codeasylum.ua.woxapptest.database

import android.arch.persistence.room.*
import codeasylum.ua.woxapptest.entity.DonePoint

@Dao
interface DonePointDao {

    @Query("SELECT * FROM done_point")
    fun getDonePoints(): List<DonePoint>

    @Query("SELECT * FROM done_point WHERE id = :id")
    fun getDonePointById(id: Long): DonePoint

    @Query("SELECT * FROM done_point WHERE route_id = :done_route_id")
    fun getDonePointsByDoneRoute(done_route_id: Long): List<DonePoint>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(donePoint: DonePoint) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dataList : List<DonePoint>)

    @Update
    fun update(donePoint: DonePoint)

    @Query("DELETE FROM done_point WHERE id = :id")
    fun delete(id: Long)

    @Delete
    fun delete(donePoint: DonePoint)
}