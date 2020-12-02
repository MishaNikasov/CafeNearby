package com.nikasov.cafenearby.ui.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikasov.cafenearby.data.network.model.CafeModel
import com.nikasov.cafenearby.ui.adapter.CafeAdapter

@BindingAdapter("cafeList", "cafeInteraction", requireAll = false)
fun RecyclerView.setCafeList(cafeList: List<CafeModel>, interaction: CafeAdapter.Interaction?) {
    if (!cafeList.isNullOrEmpty()) {
        val cafeAdapter = CafeAdapter()
        cafeAdapter.submitList(cafeList)
        apply {
            adapter = cafeAdapter
            layoutManager = LinearLayoutManager(context)
        }
        interaction?.let {
            cafeAdapter.interaction = interaction
            cafeAdapter.isAutomaticallyChanged = false
        }
    }
}
