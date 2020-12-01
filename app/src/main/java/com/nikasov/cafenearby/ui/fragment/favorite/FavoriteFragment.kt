package com.nikasov.cafenearby.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nikasov.cafenearby.R
import com.nikasov.cafenearby.data.network.model.CafeModel
import com.nikasov.cafenearby.databinding.FragmentFavoriteBinding
import com.nikasov.cafenearby.ui.adapter.CafeAdapter
import com.nikasov.cafenearby.ui.fragment.base.BaseFragment
import com.nikasov.cafenearby.ui.fragment.cafe.CafePageFragment
import com.nikasov.cafenearby.viewmodel.CafeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding>() {

    private val viewModel: CafeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
        setupViews()
    }

    private fun setupViews() {
        binding.cafeInteraction = cafeInteraction
    }

    private fun loadData() {
        viewModel.getFavoriteCafe()
    }

    private val cafeInteraction = object: CafeAdapter.Interaction {
        override fun onItemSelected(position: Int, item: CafeModel) {
            val bundle = CafePageFragment.getBundle(item.id)
//            findNavController().navigate(R.id.actionToCafePageFragment, bundle)
        }

        override fun addToFavorite(item: CafeModel) {
            Timber.d("${item.title} added to favorite")
            viewModel.addCafeToFavorite(item)
        }

        override fun deleteFromFavorite(item: CafeModel) {
            Timber.d("${item.title} deleted from favorite")
            viewModel.deleteFromFavorite(item)
        }
    }

    override fun refresh() {

    }
}