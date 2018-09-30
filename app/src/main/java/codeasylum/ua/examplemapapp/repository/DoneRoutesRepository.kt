package codeasylum.ua.examplemapapp.repository

import android.os.AsyncTask
import codeasylum.ua.examplemapapp.App
import codeasylum.ua.examplemapapp.entity.DoneRoute

class DoneRoutesRepository : Repository<DoneRoute> {

    override fun getData(onGetDataResultCallback: OnGetDataResultCallback<DoneRoute>, queryMap: MutableMap<String, String>) {
        getData(onGetDataResultCallback)
    }

    override fun insertAll(dataList: List<DoneRoute>) {

    }

    override fun insertData(item: DoneRoute, onDataInsertedCallback: OnDataInsertedCallback) {
        AsyncTask.execute {
            onDataInsertedCallback.onDataInsert(App.routeHistoryDatabase.DoneRouteDao().insert(item))
        }
    }

    override fun deleteData(item: DoneRoute) {
        AsyncTask.execute {
            App.routeHistoryDatabase.DoneRouteDao().delete(item)
        }
    }

    override fun deleteData(id: Int) {
        AsyncTask.execute {
            App.routeHistoryDatabase.DoneRouteDao().delete(id.toLong())
        }
    }


    override fun getData(onGetDataResultCallback: OnGetDataResultCallback<DoneRoute>) {
        AsyncTask.execute {
            App.routeHistoryDatabase.DoneRouteDao().getDoneRoutes().apply {
                onGetDataResultCallback.onSuccess(this)
            }

        }
    }

}