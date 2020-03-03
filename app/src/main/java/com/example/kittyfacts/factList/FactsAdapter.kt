package com.example.kittyfacts.factList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.FactItemModel
import com.example.kittyfacts.databinding.FactItemBinding

class FactsAdapter(
    private val viewModel: FactsViewModel
) : ListAdapter<FactItemModel, FactsAdapter.ViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)

        if (isuserReachedTheBottom(position) && repositoryHasMoreItems()) {
            viewModel.loadFacts()
        }
    }

    private fun repositoryHasMoreItems(): Boolean {
        viewModel.totalItemsSize.value?.let { totalItemSize ->
            viewModel.items.value?.size?.let { currentItemSize ->
                return totalItemSize > currentItemSize
            }
        }
        return false
    }

    private fun isuserReachedTheBottom(position: Int) : Boolean
            = position + 1 == viewModel.items.value?.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: FactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: FactsViewModel, newsModel: FactItemModel) {

            binding.viewmodel = viewModel
            binding.factItem = newsModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FactItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<FactItemModel>() {
            override fun areItemsTheSame(oldItem: FactItemModel, newItem: FactItemModel): Boolean =
                oldItem.factNumber == newItem.factNumber

            override fun areContentsTheSame(
                oldItem: FactItemModel,
                newItem: FactItemModel
            ): Boolean =
                oldItem == newItem
        }
    }
}