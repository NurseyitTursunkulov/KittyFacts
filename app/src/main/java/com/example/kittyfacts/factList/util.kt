package com.example.kittyfacts.factList

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

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
        Log.e("Nurs","ViewModel not initialized when attempting to set up adapter.")
    }
    viewModel?.items?.observe(viewLifecycleOwner, Observer {
        it?.let {
            listAdapter.submitList(it)
        }
    })
}
