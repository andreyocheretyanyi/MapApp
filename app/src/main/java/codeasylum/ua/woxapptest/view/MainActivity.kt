package codeasylum.ua.woxapptest.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import codeasylum.ua.woxapptest.R
import codeasylum.ua.woxapptest.dialog.ProgressDialogHelper
import codeasylum.ua.woxapptest.entity.*
import codeasylum.ua.woxapptest.viewModel.MainViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity(), OnMapReadyCallback {


    private val mainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    private var step = MainViewModel.AddPointStep.ADD_FROM
    private lateinit var googleMap: GoogleMap
    private val mapFragment by lazy {
        MapFragment.newInstance()
    }
    private var carMarker: Marker? = null
    private var animation: ValueAnimator? = null

    private val donePointLiveDataObserver = Observer<DonePointHolder> {
        addPins(it!!)
    }

    private val routeObserver = Observer<List<String>> {
        drawRoute(it!!)
    }

    private val fromAddressObserver = Observer<String> {
        et_from.setText(it)
    }

    private val toAddressObserver = Observer<String> {
        et_to.setText(it)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpMapFragment()
        initListeners()
        mainViewModel.donePointsLiveData.observe(this, donePointLiveDataObserver)
        mainViewModel.routeLiveData.observe(this, routeObserver)
        mainViewModel.fromAddressLiveData.observe(this, fromAddressObserver)
        mainViewModel.toAddressLiveData.observe(this, toAddressObserver)
    }


    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            requestCode == SELECT_ADDRESS_CODE && resultCode == RESULT_OK -> {
                mainViewModel.addDonePoint(data?.getSerializableExtra(DONE_POINT_KEY) as DonePoint, step)
            }

            requestCode == HISTORY_CODE && resultCode == Activity.RESULT_OK -> {
                mainViewModel.fillDataFromHistory(data?.getSerializableExtra(SAVED_ROUTE) as DoneRoute)
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_history -> {
                startActivityForResult(Intent(this, HistoryActivity::class.java), HISTORY_CODE)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initListeners() {
        btn_add_point.setOnClickListener {
            step = MainViewModel.AddPointStep.ADD_ADDITIONAL_POINT
            startSelectAddressCodeActivity()
        }

        btn_clear.setOnClickListener {
            animation?.takeIf { anim ->
                anim.isRunning
            }?.run {
                this.cancel()
                return@setOnClickListener

            }
            mainViewModel.clearAllPoints()

        }

        btn_go_car.setOnClickListener {
            animation = createAnimation()
            animation?.start()
            mainViewModel.saveDoneRoute(DoneRoute(null, "${et_from.text} - ${et_to.text}"))
        }
        et_from.setOnClickListener {
            step = MainViewModel.AddPointStep.ADD_FROM
            startSelectAddressCodeActivity()
        }

        et_to.setOnClickListener {
            step = MainViewModel.AddPointStep.ADD_TO
            startSelectAddressCodeActivity()
        }
    }

    private fun createAnimation(): ValueAnimator? = mainViewModel.routeLiveData.value?.let { shapes ->
        val vla = ValueAnimator.ofInt(0, shapes.lastIndex)
        vla.duration = 12000
        vla.interpolator = FastOutSlowInInterpolator()
        vla.addListener(object : Animator.AnimatorListener {

            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                mainViewModel.clearAllPoints()
            }

            override fun onAnimationCancel(p0: Animator?) {
                mainViewModel.clearAllPoints()
            }

            override fun onAnimationStart(p0: Animator?) {
                btn_go_car.isEnabled = false
                btn_add_point.isEnabled = false
            }

        })
        vla.addUpdateListener {
            carMarker?.position = mainViewModel.convertShapeToLatLng(shapes[it.animatedValue as Int])
        }
        vla
    }


    private fun startSelectAddressCodeActivity() {
        startActivityForResult(Intent(this, SelectStreetActivity::class.java), SELECT_ADDRESS_CODE)
    }


    private fun addPins(donePointHolder: DonePointHolder) {
        googleMap.clear()
        addMarker(donePointHolder.from)
        addMarker(donePointHolder.to)
        for (point in donePointHolder.addtionalPoints) {
            addMarker(point)
        }

        checkFromToPointsAndGetRoute(donePointHolder)

    }


    private fun checkFromToPointsAndGetRoute(donePointHolder: DonePointHolder) {
        if (donePointHolder.from != null && donePointHolder.to != null) {
            carMarker = googleMap.addMarker(MarkerOptions().position(LatLng(donePointHolder.from!!.lat, donePointHolder.from!!.lng)))
            carMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
            btn_go_car.isEnabled = true
            btn_add_point.isEnabled = true
            mainViewModel.getRoutes(getString(R.string.app_here_id), getString(R.string.app_here_code),
                    mainViewModel.formatLatLngToGeoString(donePointHolder.from!!.lat, donePointHolder.from!!.lng),
                    mainViewModel.formatLatLngToGeoString(donePointHolder.to!!.lat, donePointHolder.to!!.lng)
            )
            ProgressDialogHelper.show(this)
        } else {
            carMarker?.remove()
            btn_go_car.isEnabled = false
            btn_add_point.isEnabled = false
        }
    }

    private fun drawRoute(list: List<String>) {
        val options = PolylineOptions().width(5f).color(Color.BLUE).geodesic(true)
        for (point in list) {
            options.add(mainViewModel.convertShapeToLatLng(point))
        }

        googleMap.addPolyline(options)
        ProgressDialogHelper.dismiss()
    }


    private fun addMarker(donePoint: DonePoint?) = donePoint?.apply {
        googleMap.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(address))
    }


    private fun setUpMapFragment() {
        fragmentManager.beginTransaction().replace(R.id.map, mapFragment).commit()
        mapFragment.getMapAsync(this)
    }


}
