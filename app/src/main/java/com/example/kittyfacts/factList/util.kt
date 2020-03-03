package com.example.kittyfacts.factList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.FactItemModel

fun FactsFragment.setupListAdapter() {
    val viewModel = viewDataBinding.viewmodel
    if (viewModel != null) {
        listAdapter = FactsAdapter(viewModel)
        viewDataBinding.newsList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        viewDataBinding.lifecycleOwner = this
    } else {
        Log.e("Nurs", "ViewModel not initialized when attempting to set up adapter.")
    }
    viewModel?.items?.observe(viewLifecycleOwner, Observer {
        it?.let {
            listAdapter.submitList(it)
        }
    })
}

fun MutableLiveData<MutableList<FactItemModel>>.postListValue(
    factsResult: List<FactItemModel>
) {
    val list = mutableListOf<FactItemModel>()
    this.value?.let {
        list.addAll(it)
    }
    list.addAll(factsResult)
    this.postValue(list)
}