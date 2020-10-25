package com.nikasov.cafenearby.ui.fragment.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.nikasov.cafenearby.R
import com.nikasov.cafenearby.databinding.FragmentMapBinding
import com.nikasov.cafenearby.ui.fragment.BaseFragment
import com.nikasov.cafenearby.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment: BaseFragment<FragmentMapBinding>() {

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

    override fun refresh() {

    }
}