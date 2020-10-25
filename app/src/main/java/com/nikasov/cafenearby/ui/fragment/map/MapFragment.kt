package com.nikasov.cafenearby.ui.fragment.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nikasov.cafenearby.R
import com.nikasov.cafenearby.databinding.FragmentMapBinding
import com.nikasov.cafenearby.ui.fragment.BaseFragment
import com.nikasov.cafenearby.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*


@AndroidEntryPoint
class MapFragment: BaseFragment<FragmentMapBinding>() {

    private val viewModel: MapViewModel by viewModels()
    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView?.onCreate(savedInstanceState)
        setupViews()
    }

    override fun setupViews() {
        mapView.getMapAsync {
            map = it
            setupMap()
        }
    }

    private fun setupMap() {
        viewModel.mapIsLoaded.postValue(true)

        val coord = LatLng(-34.0, 151.0)
        map?.apply {
            addMarker(
                MarkerOptions()
                    .position(coord)
                    .title("Marker in Sydney")
            )

            val cameraPosition = CameraPosition.Builder()
                .target(coord)
                .zoom(12f)
                .build()

            animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
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

    override fun refresh() { }
}