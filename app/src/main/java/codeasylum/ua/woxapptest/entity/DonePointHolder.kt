package codeasylum.ua.woxapptest.entity

data class DonePointHolder(var from: DonePoint? = null,
                           var to: DonePoint? = null,
                           val addtionalPoints: ArrayList<DonePoint> = ArrayList()) {
}