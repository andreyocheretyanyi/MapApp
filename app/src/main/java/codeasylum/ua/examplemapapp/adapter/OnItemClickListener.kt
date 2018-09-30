package codeasylum.ua.examplemapapp.adapter

interface OnItemClickListener<T> {
    fun onItemClick(position: Int, data: T)
}