package codeasylum.ua.examplemapapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import codeasylum.ua.examplemapapp.entity.DonePoint
import codeasylum.ua.examplemapapp.entity.DoneRoute

@Database(entities = [DonePoint::class, DoneRoute::class], version = 1)
abstract class RouteHistoryDatabase : RoomDatabase() {
    abstract fun DonePointDao(): DonePointDao
    abstract fun DoneRouteDao(): DoneRouteDao
}