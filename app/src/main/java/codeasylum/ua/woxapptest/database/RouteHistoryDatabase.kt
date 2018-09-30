package codeasylum.ua.woxapptest.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import codeasylum.ua.woxapptest.entity.DonePoint
import codeasylum.ua.woxapptest.entity.DoneRoute

@Database(entities = [DonePoint::class, DoneRoute::class], version = 1)
abstract class RouteHistoryDatabase : RoomDatabase() {
    abstract fun DonePointDao(): DonePointDao
    abstract fun DoneRouteDao(): DoneRouteDao
}