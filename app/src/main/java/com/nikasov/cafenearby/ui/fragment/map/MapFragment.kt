package com.nikasov.cafenearby.ui.fragment.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.nikasov.cafenearby.R
import com.nikasov.cafenearby.databinding.FragmentMapBinding
import com.nikasov.cafenearby.ui.fragment.BaseFragment
import com.nikasov.cafenearby.utils.hasLocationPermission
import com.nikasov.cafenearby.utils.requestLocationPermission
import com.nikasov.cafenearby.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.progress_view.*

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>() {

    private val viewModel: MapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView?.onCreate(savedInstanceState)

        setupViews()
        setupViewModelCallbacks()
    }

    override fun setupViews() {
        loadMap()
    }

    private fun setupViewModelCallbacks() {
        viewModel.apply {

            map.observe(viewLifecycleOwner, {
                getCurrentLocation()
            })

            coordinates.observe(viewLifecycleOwner, { currentCoordinates ->
                currentCoordinates?.let {
                    setupMap()
                }
            })

            mapIsLoading.observe(viewLifecycleOwner, {
                if (it) {
                    progressLoader.visibility = View.VISIBLE
                } else {
                    progressLoader.visibility = View.GONE
                }
            })
        }
    }

    private fun loadMap() {
        mapView.getMapAsync { googleMap ->
            viewModel.map.postValue(googleMap)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (requireContext().hasLocationPermission()) {
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            val lastLocation = fusedLocationProviderClient?.lastLocation

            lastLocation?.addOnSuccessListener { location ->
                location?.let {
                    viewModel.coordinates.postValue(LatLng(it.latitude, it.longitude))
                }
            }

        } else {
            requestLocationPermission()
        }
    }


    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun refresh() {}
}