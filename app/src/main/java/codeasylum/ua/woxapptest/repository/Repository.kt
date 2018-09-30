package codeasylum.ua.woxapptest.repository


interface Repository<T> {

    fun getData(onGetDataResultCallback: OnGetDataResultCallback<T>)

    fun getData(onGetDataResultCallback: OnGetDataResultCallback<T>, queryMap: MutableMap<String, String>)

    fun insertData(item: T, onDataInsertedCallback: OnDataInsertedCallback)

    fun deleteData(item: T)

    fun deleteData(id: Int)

    fun insertAll(dataList: List<T>)


}