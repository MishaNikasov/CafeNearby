package com.nikasov.cafenearby.ui.fragment.cafe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.nikasov.cafenearby.R
import com.nikasov.cafenearby.databinding.FragmentCafePageBinding
import com.nikasov.cafenearby.ui.fragment.base.BaseFragment
import com.nikasov.cafenearby.utils.UiState
import com.nikasov.cafenearby.viewmodel.CafeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafePageFragment : BaseFragment<FragmentCafePageBinding>() {

    companion object {
        private const val PLACE_ID = "place_id"

        fun getBundle(placeId: String?): Bundle {
            val bundle = Bundle()
            bundle.putString(PLACE_ID, placeId)
            return bundle
        }
    }

    private val viewModel: CafeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cafe_page, container, false)
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

    private fun setupViews() {
    }

    private fun loadData() {
        arguments?.getString(PLACE_ID)?.let {
            viewModel.getCafeDetails(it)
        }
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

    override fun refresh() {}
}