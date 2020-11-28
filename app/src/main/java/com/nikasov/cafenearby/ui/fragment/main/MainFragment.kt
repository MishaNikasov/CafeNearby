package com.nikasov.cafenearby.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.nikasov.cafenearby.R
import com.nikasov.cafenearby.databinding.FragmentMainBinding
import com.nikasov.cafenearby.ui.fragment.base.BaseFragment
import com.nikasov.cafenearby.utils.hasLocationPermission
import com.nikasov.cafenearby.utils.requestLocationPermission
import com.nikasov.cafenearby.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
//        setupCafeList()
    }

    private fun setupCafeList() {
        if (requireContext().hasLocationPermission()) {
            viewModel.getCafeList()
        } else {
            requestLocationPermission()
        }
    }

    override fun refresh() { }

}