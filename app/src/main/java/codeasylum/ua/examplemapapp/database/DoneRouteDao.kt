package codeasylum.ua.examplemapapp.database

import android.arch.persistence.room.*
import codeasylum.ua.examplemapapp.entity.DoneRoute

@Dao
interface DoneRouteDao {

    @Query("SELECT * FROM done_routes")
    fun getDoneRoutes(): List<DoneRoute>

    @Query("SELECT * FROM done_routes WHERE id = :id")
    fun getDoneRouteById(id: Long): DoneRoute

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(doneRoute: DoneRoute) : Long

    @Update
    fun update(doneRoute: DoneRoute)

    @Query("DELETE FROM done_routes WHERE id = :id")
    fun delete(id: Long)

    @Delete
    fun delete(doneRoute: DoneRoute)
}