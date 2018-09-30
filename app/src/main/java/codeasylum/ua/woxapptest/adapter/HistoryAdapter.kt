package codeasylum.ua.woxapptest.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import codeasylum.ua.woxapptest.R
import codeasylum.ua.woxapptest.entity.DoneRoute
import kotlinx.android.synthetic.main.item_text.view.*

class HistoryAdapter(val context: Context, val data: List<DoneRoute>, val onItemClickListener: OnItemClickListener<DoneRoute>) : RecyclerView.Adapter<DefaultViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): DefaultViewHolder = DefaultViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text, viewGroup, false))


    override fun getItemCount(): Int = data.size



    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val doneRoute = data[position]
        with(holder.itemView){
            setOnClickListener {
                onItemClickListener.onItemClick(position,doneRoute)
            }
            tv_address_text.text = doneRoute.base_data
        }

    }


}