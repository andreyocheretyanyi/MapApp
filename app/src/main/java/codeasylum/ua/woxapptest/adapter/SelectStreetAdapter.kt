package codeasylum.ua.woxapptest.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import codeasylum.ua.woxapptest.R
import codeasylum.ua.woxapptest.entity.DonePoint
import codeasylum.ua.woxapptest.entity.geocodeResult.Result
import kotlinx.android.synthetic.main.item_text.view.*

class SelectStreetAdapter(val context: Context, val data: List<Result>, private val onItemClickListenerListener: OnItemClickListener<DonePoint>) : RecyclerView.Adapter<DefaultViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): DefaultViewHolder = DefaultViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text, viewGroup, false))


    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(vh: DefaultViewHolder, position: Int) {
        val item = data[position].location
        with(vh.itemView) {
            setOnClickListener {
                onItemClickListenerListener.onItemClick(position, DonePoint(null, item.address.label, item.displayPosition.latitude, item.displayPosition.longitude))
            }
            tv_address_text.text = item.address.label
        }
    }
}

