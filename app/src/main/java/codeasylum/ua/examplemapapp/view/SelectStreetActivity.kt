package codeasylum.ua.examplemapapp.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import codeasylum.ua.examplemapapp.R
import codeasylum.ua.examplemapapp.adapter.OnItemClickListener
import codeasylum.ua.examplemapapp.adapter.SelectStreetAdapter
import codeasylum.ua.examplemapapp.entity.DONE_POINT_KEY
import codeasylum.ua.examplemapapp.entity.DonePoint
import codeasylum.ua.examplemapapp.entity.geocodeResult.Result
import codeasylum.ua.examplemapapp.viewModel.SelectStreetViewModel
import kotlinx.android.synthetic.main.activity_select_street.*



class SelectStreetActivity : AppCompatActivity(), OnItemClickListener<DonePoint> {

    override fun onItemClick(position: Int, data: DonePoint) {

        setResult(Activity.RESULT_OK, Intent().putExtra(DONE_POINT_KEY, data))
        finish()
    }

    private val lm by lazy {
        LinearLayoutManager(this)
    }

    private val selectStreetViewModel by lazy {
        ViewModelProviders.of(this).get(SelectStreetViewModel::class.java)
    }

    private val geocodeResultObserver = Observer<List<Result>> {
        rv_select_item.adapter = SelectStreetAdapter(this, it!!, this)
    }

    private val errorMessageObserver = Observer<String> {
        Log.d("tag", it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_street)
        setSupportActionBar(toolbar)
        iv_back.setOnClickListener { onBackPressed() }
        initObservers()
        initListeners()
        rv_select_item.layoutManager = lm
    }

    private fun initObservers() {
        selectStreetViewModel.errorLiveData.observe(this, errorMessageObserver)
        selectStreetViewModel.geocodeResultLiveData.observe(this, geocodeResultObserver)
    }

    private fun initListeners() {
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_search.text.length > 2) {
                    makeGeocodeRequest(et_search)
                }
            }

        })
    }

    private fun makeGeocodeRequest(editText: EditText) {
        selectStreetViewModel.getGeocodeResult(editText.text.toString(), getString(R.string.app_here_id), getString(R.string.app_here_code))
    }


}