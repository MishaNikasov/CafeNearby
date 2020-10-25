package com.nikasov.cafenearby.ui.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.nikasov.cafenearby.utils.hideKeyBoard
import kotlinx.android.synthetic.*

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.hideKeyBoard()
    }

    abstract fun setupViews()
    abstract fun refresh()

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyBoard()
        activity?.hideKeyBoard()
        clearFindViewByIdCache()
    }

}