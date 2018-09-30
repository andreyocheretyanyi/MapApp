package codeasylum.ua.woxapptest.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import codeasylum.ua.woxapptest.api.RetrofitManager
import codeasylum.ua.woxapptest.entity.*
import codeasylum.ua.woxapptest.repository.*
import com.google.android.gms.maps.model.LatLng

private const val MAX_ADDITIONAL_POINTS = 5

class MainViewModel : ViewModel() {

    enum class AddPointStep {
        ADD_FROM,
        ADD_TO,
        ADD_ADDITIONAL_POINT
    }

    val donePointsLiveData = MutableLiveData<DonePointHolder>()
    val routeLiveData = MutableLiveData<List<String>>()
    val errorLiveData = MutableLiveData<String>()
    val fromAddressLiveData = MutableLiveData<String>()
    val toAddressLiveData = MutableLiveData<String>()
    private var donePoints = DonePointHolder()

    private val routeRepository by lazy {
        RouteRepository(RetrofitManager.getClient()!!)
    }

    private val doneRoutesRepository by lazy {
        DoneRoutesRepository()
    }

    private val donePointRepository by lazy {
        DonePointRepository()
    }


    private val routeResultCallback = object : OnGetDataResultCallback<String> {
        override fun onSuccess(data: List<String>) {
            routeLiveData.postValue(data)
        }

        override fun onError(message: String) {
            errorLiveData.postValue(message)
        }

    }

    fun addDonePoint(donePoint: DonePoint, addPonintStep: AddPointStep) {
        when (addPonintStep) {
            AddPointStep.ADD_FROM -> {
                donePoints.from = donePoint
                fromAddressLiveData.postValue(donePoint.address)
            }
            MainViewModel.AddPointStep.ADD_TO -> {
                donePoints.to = donePoint
                toAddressLiveData.postValue(donePoint.address)
            }
            MainViewModel.AddPointStep.ADD_ADDITIONAL_POINT -> {
                addAdditionalPoint(donePoint)
            }
        }
        updateLiveData()
    }

    private fun addAdditionalPoint(donePoint: DonePoint) {
        if (donePoints.addtionalPoints.size < MAX_ADDITIONAL_POINTS) {
            donePoints.addtionalPoints.add(donePoint)
        }
    }


    fun clearAllPoints() {
        donePoints = DonePointHolder()
        fromAddressLiveData.postValue("")
        toAddressLiveData.postValue("")
        updateLiveData()
    }


    private fun updateLiveData() {
        donePointsLiveData.postValue(donePoints)
    }

    fun formatLatLngToGeoString(lat: Double, lng: Double): String = "geo!$lat,$lng"

    fun getRoutes(app_id: String, ap_code: String, fromLatLngString: String, toLatLngString: String) {
        val query = mutableMapOf(APP_ID_KEY to app_id, APP_CODE_KEY to ap_code,
                "${WAYPOINT}0" to fromLatLngString,
                "${WAYPOINT}${donePoints.addtionalPoints.size + 1}" to toLatLngString)

        if (donePoints.addtionalPoints.isNotEmpty()) {
            for (i in 1..donePoints.addtionalPoints.size) {
                val item = donePoints.addtionalPoints[i - 1]
                query["${WAYPOINT}$i"] = formatLatLngToGeoString(item.lat, item.lng)
            }
        }

        routeRepository.getData(routeResultCallback, query)
    }

    fun saveDoneRoute(doneRoute: DoneRoute) {
        doneRoutesRepository.insertData(doneRoute, object : OnDataInsertedCallback {
            override fun onDataInsert(id: Long) {
                val listForInsert = ArrayList<DonePoint>()
                listForInsert.add(donePoints.from!!)
                listForInsert.addAll(donePoints.addtionalPoints)
                listForInsert.add(donePoints.to!!)
                for (item in listForInsert) {
                    item.route_id = id
                }
                donePointRepository.insertAll(listForInsert)

            }
        })
    }

    fun convertShapeToLatLng(shape: String): LatLng = shape.let {
        val coordinates = it.split(",")
        LatLng(coordinates[0].toDouble(), coordinates[1].toDouble())
    }

    fun fillDataFromHistory(doneRoute: DoneRoute) {
        clearAllPoints()
        donePointRepository.getData(object : OnGetDataResultCallback<DonePoint> {
            override fun onSuccess(data: List<DonePoint>) {
                for (i in 0 until data.size) {
                    when (i) {
                        0 -> addDonePoint(data[i], AddPointStep.ADD_FROM)
                        data.lastIndex -> addDonePoint(data[i], AddPointStep.ADD_TO)
                        else -> addDonePoint(data[i], AddPointStep.ADD_ADDITIONAL_POINT)
                    }
                }
            }

            override fun onError(message: String) {
            }


        }, mutableMapOf(PARENT_ROUTE_ID to doneRoute.id.toString()))
    }
}