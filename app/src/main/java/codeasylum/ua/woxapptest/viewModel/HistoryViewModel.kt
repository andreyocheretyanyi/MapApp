package codeasylum.ua.woxapptest.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import codeasylum.ua.woxapptest.entity.DoneRoute
import codeasylum.ua.woxapptest.repository.DoneRoutesRepository
import codeasylum.ua.woxapptest.repository.OnGetDataResultCallback

class HistoryViewModel : ViewModel() {

    private val doneRoutesRepository by lazy {
        DoneRoutesRepository()
    }
    val doneRoutesLiveData: MutableLiveData<List<DoneRoute>> = MutableLiveData()

    fun getDoneRoutesList() {
        doneRoutesRepository.getData(object : OnGetDataResultCallback<DoneRoute> {

            override fun onSuccess(data: List<DoneRoute>) {
                doneRoutesLiveData.postValue(data)
            }

            override fun onError(message: String) {

            }

        })
    }

}