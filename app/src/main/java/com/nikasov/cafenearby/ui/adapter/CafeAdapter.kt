package com.nikasov.cafenearby.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikasov.cafenearby.data.network.model.CafeModel
import com.nikasov.cafenearby.databinding.ItemCafeBinding
import javax.inject.Inject

class CafeAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var interaction: Interaction? = null
    var isAutomaticallyChanged = true

    private val callback = object : DiffUtil.ItemCallback<CafeModel>() {
        override fun areItemsTheSame(oldItem: CafeModel, newItem: CafeModel): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: CafeModel, newItem: CafeModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CafeViewHolder(
            ItemCafeBinding.inflate((LayoutInflater.from(parent.context)), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CafeViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<CafeModel>) {
        differ.submitList(list)
    }

    inner class CafeViewHolder constructor(
        private val binding: ItemCafeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CafeModel) = with(itemView) {
            binding.cafe = item
            binding.favoriteBtn.setOnCheckedChangeListener { _, isChecked ->
                item.isFavorite = isChecked
                if (isChecked) {
                    if (!isAutomaticallyChanged) {
                        interaction?.addToFavorite(item)
                    }
                } else {
                    if (!isAutomaticallyChanged) {
                        interaction?.deleteFromFavorite(item)
                    }
                }
            }
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: CafeModel)
        fun addToFavorite(item: CafeModel)
        fun deleteFromFavorite(item: CafeModel)
    }
}
