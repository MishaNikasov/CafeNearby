package com.nikasov.cafenearby.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.nikasov.cafenearby.R
import com.nikasov.cafenearby.databinding.FragmentFavoriteBinding
import com.nikasov.cafenearby.ui.fragment.BaseFragment

class FavoriteFragment: BaseFragment<FragmentFavoriteBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun refresh() {

    }
}