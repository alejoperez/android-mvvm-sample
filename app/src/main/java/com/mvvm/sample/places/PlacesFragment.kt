package com.mvvm.sample.places

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseFragment
import com.mvvm.sample.data.room.Place
import com.mvvm.sample.livedata.DataResource
import com.mvvm.sample.livedata.Status
import kotlinx.android.synthetic.main.fragment_places.*

class PlacesFragment: BaseFragment(), OnMapReadyCallback {

    companion object {
        private const val ZOOM = 4f
        const val TAG = "PlacesFragment"
        fun newInstance() = PlacesFragment()
    }

    private val viewModel by lazy { obtainViewModel(PlacesViewModel::class.java) }

    private val placesResponseObserver = Observer<DataResource<List<Place>>>{
        if (it != null) {
            onPlacesResponse(it)
        } else {
            onPlacesFailure()
        }
    }

    private lateinit var googleMap: GoogleMap

    private var currentPlaces: List<Place>? = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_places,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initViewModel() {
        viewModel.places.observe(this,placesResponseObserver)
    }

    private fun initView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fragmentMapPlaces) as SupportMapFragment
        mapFragment.getMapAsync(this)

        placesFabRandom.setOnClickListener { fabView ->
            randomPlace(); Snackbar.make(fabView, R.string.places_random_message, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onMapReady(gm: GoogleMap) {
        googleMap = gm
        viewModel.getPlaces()
    }

    private fun onPlacesResponse(response: DataResource<List<Place>>) {
        when(response.status) {
            Status.SUCCESS -> onPlacesSuccess(response.data)
            Status.FAILURE -> onPlacesFailure()
            Status.NETWORK_ERROR -> onNetworkError()
        }
    }

    private fun onPlacesSuccess(places: List<Place>?) {
        currentPlaces = places
        loadPlacesInMap()
        randomPlace()
    }

    private fun onPlacesFailure() {
        showAlert(R.string.error_loading_places)
    }

    private fun loadPlacesInMap() {
        currentPlaces?.let {
            for (p in it) {
                googleMap.addMarker(buildMarkerPlace(p))
            }
        }
    }

    private fun buildMarkerPlace(p: Place): MarkerOptions {
        return MarkerOptions()
                .position(LatLng(p.lat, p.lon))
                .title(p.companyName)
                .snippet(p.address)
    }

    private fun randomPlace() {
        currentPlaces?.let {
            val randomPosition = (0 until it.size).shuffled().first()
            val place = it[randomPosition]
            val cameraPosition = CameraPosition.Builder().target(LatLng(place.lat, place.lon)).zoom(ZOOM).build()
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

}