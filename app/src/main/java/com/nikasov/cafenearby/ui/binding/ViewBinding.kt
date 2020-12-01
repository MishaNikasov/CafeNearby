package com.nikasov.cafenearby.ui.binding

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import com.nikasov.cafenearby.utils.showToast

@BindingAdapter("errorToast")
fun ViewGroup.showErrorMessage(errorToast: String?) {
    errorToast?.let {
        this.context.showToast(errorToast)
    }
}