package com.nikasov.cafenearby.ui.fragment.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.nikasov.cafenearby.utils.hideKeyBoard
import com.nikasov.cafenearby.utils.showToast
import kotlinx.android.synthetic.*

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.hideKeyBoard()
    }

    fun showError(errorMessage: String) {
        requireContext().showToast(errorMessage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyBoard()
        activity?.hideKeyBoard()
        clearFindViewByIdCache()
    }

    abstract fun refresh()

}