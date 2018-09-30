package codeasylum.ua.woxapptest.adapter

interface OnItemClickListener<T> {
    fun onItemClick(position: Int, data: T)
}