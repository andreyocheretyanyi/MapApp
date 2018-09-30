package codeasylum.ua.woxapptest.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import codeasylum.ua.woxapptest.R
import codeasylum.ua.woxapptest.adapter.HistoryAdapter
import codeasylum.ua.woxapptest.adapter.OnItemClickListener
import codeasylum.ua.woxapptest.entity.DoneRoute
import codeasylum.ua.woxapptest.entity.SAVED_ROUTE
import codeasylum.ua.woxapptest.viewModel.HistoryViewModel
import kotlinx.android.synthetic.main.activity_history.*



class HistoryActivity : AppCompatActivity(), OnItemClickListener<DoneRoute> {

    private val historyViewModel by lazy {
        ViewModelProviders.of(this).get(HistoryViewModel::class.java)
    }

    private val doneRoutesLiveDataObserver: Observer<List<DoneRoute>> = Observer {
        rv_history.adapter = HistoryAdapter(this, it!!, this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        historyViewModel.doneRoutesLiveData.observe(this, doneRoutesLiveDataObserver)
        rv_history.layoutManager = LinearLayoutManager(this)
        historyViewModel.getDoneRoutesList()
    }

    override fun onItemClick(position: Int, data: DoneRoute) {
        setResult(Activity.RESULT_OK, Intent().putExtra(SAVED_ROUTE,data))
        finish()
    }


}
