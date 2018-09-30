package codeasylum.ua.examplemapapp

import android.app.Application
import android.arch.persistence.room.Room
import codeasylum.ua.examplemapapp.database.RouteHistoryDatabase

class App : Application() {

    companion object {
        lateinit var routeHistoryDatabase: RouteHistoryDatabase

    }

    override fun onCreate() {
        super.onCreate()
        routeHistoryDatabase = Room.databaseBuilder(this, RouteHistoryDatabase::class.java, "RouteHistoryDatabase").build()
    }


}