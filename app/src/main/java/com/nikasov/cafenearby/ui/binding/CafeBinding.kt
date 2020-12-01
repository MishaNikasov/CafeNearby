package com.nikasov.cafenearby.ui.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikasov.cafenearby.data.network.model.CafeModel
import com.nikasov.cafenearby.ui.adapter.CafeAdapter

@BindingAdapter("cafeList", "cafeInteraction")
fun RecyclerView.setCafeList(cafeList: List<CafeModel>, interaction: CafeAdapter.Interaction?) {
    if (!cafeList.isNullOrEmpty()) {
        val cafeAdapter = CafeAdapter()
        cafeAdapter.submitList(cafeList)
        interaction?.let {
            cafeAdapter.interaction = interaction
        }
        apply {
            adapter = cafeAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
