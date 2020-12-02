package com.nikasov.cafenearby.ui.fragment.cafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nikasov.cafenearby.R
import com.nikasov.cafenearby.data.network.model.CafeModel
import com.nikasov.cafenearby.databinding.FragmentCafeListBinding
import com.nikasov.cafenearby.ui.adapter.CafeAdapter
import com.nikasov.cafenearby.ui.fragment.base.BaseFragment
import com.nikasov.cafenearby.utils.UiState
import com.nikasov.cafenearby.utils.hasLocationPermission
import com.nikasov.cafenearby.utils.requestLocationPermission
import com.nikasov.cafenearby.viewmodel.CafeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CafeListFragment : BaseFragment<FragmentCafeListBinding>() {

    private val viewModel: CafeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cafe_list, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
        setupViews()
        setupViewModelCallbacks()
    }

    private fun loadData() {
        if (viewModel.cafeList.value.isNullOrEmpty()) {
//            setupCafeList()
        }
    }

    private fun setupViews() {
        binding.cafeInteraction = cafeInteraction
    }

    private fun setupViewModelCallbacks() {
        viewModel.apply {
            uiState.observe(viewLifecycleOwner, { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progress.isVisible = true
                    }
                    is UiState.Success -> {
                        binding.progress.isVisible = false
                    }
                    is UiState.Error -> {
                        binding.progress.isVisible = false
                        showError(state.message)
                    }
                    else -> {
                        return@observe
                    }
                }
            })
        }
    }

    private fun setupCafeList() {
        if (requireContext().hasLocationPermission()) {
            viewModel.getCafeList()
        } else {
            requestLocationPermission()
        }
    }

    private val cafeInteraction = object: CafeAdapter.Interaction {
        override fun onItemSelected(position: Int, item: CafeModel) {
            val bundle = CafePageFragment.getBundle(item.id)
            findNavController().navigate(R.id.actionToCafePageFragment, bundle)
        }

        override fun addToFavorite(item: CafeModel) {
            viewModel.addCafeToFavorite(item)
        }

        override fun deleteFromFavorite(item: CafeModel) {
            viewModel.deleteFromFavorite(item)
        }
    }

    override fun refresh() { }

}