package codeasylum.ua.examplemapapp.repository

import android.os.AsyncTask
import codeasylum.ua.examplemapapp.App
import codeasylum.ua.examplemapapp.entity.DonePoint
import codeasylum.ua.examplemapapp.entity.PARENT_ROUTE_ID


class DonePointRepository : Repository<DonePoint> {

    private var additionalData = mutableMapOf<String, String>()

    override fun getData(onGetDataResultCallback: OnGetDataResultCallback<DonePoint>, queryMap: MutableMap<String, String>) {
        additionalData = queryMap
        getData(onGetDataResultCallback)
    }

    override fun insertAll(dataList: List<DonePoint>) {
        AsyncTask.execute {
            App.routeHistoryDatabase.DonePointDao().insertAll(dataList)
        }
    }

    override fun insertData(item: DonePoint, onDataInsertedCallback: OnDataInsertedCallback) {
        AsyncTask.execute {
            onDataInsertedCallback.onDataInsert(App.routeHistoryDatabase.DonePointDao().insert(item))
        }
    }

    override fun deleteData(item: DonePoint) {
        AsyncTask.execute {
            App.routeHistoryDatabase.DonePointDao().delete(item)
        }
    }

    override fun deleteData(id: Int) {
        AsyncTask.execute {
            App.routeHistoryDatabase.DonePointDao().delete(id.toLong())
        }
    }

    override fun getData(onGetDataResultCallback: OnGetDataResultCallback<DonePoint>) {
        AsyncTask.execute {
            val data = if (additionalData.containsKey(PARENT_ROUTE_ID) && additionalData[PARENT_ROUTE_ID]?.toLongOrNull() != null) {
                App.routeHistoryDatabase.DonePointDao().getDonePointsByDoneRoute(additionalData[PARENT_ROUTE_ID]?.toLong()!!)
            } else {
                App.routeHistoryDatabase.DonePointDao().getDonePoints()
            }
            onGetDataResultCallback.onSuccess(data)

        }
    }
}