package codeasylum.ua.woxapptest.repository

interface OnGetDataResultCallback<T> {

    fun onSuccess(data: List<T>)

    fun onError(message: String)
}