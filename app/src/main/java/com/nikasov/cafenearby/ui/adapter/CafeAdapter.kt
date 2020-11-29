package com.nikasov.cafenearby.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.nikasov.cafenearby.data.network.model.CafeModel
import com.nikasov.cafenearby.databinding.ItemCafeBinding

class CafeAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val interaction: Interaction? = null

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
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: CafeModel)
    }
}