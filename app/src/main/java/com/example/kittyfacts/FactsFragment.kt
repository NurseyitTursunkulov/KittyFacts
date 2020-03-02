package com.example.kittyfacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kittyfacts.databinding.FragmentFactsBinding
import com.example.kittyfacts.factList.FactsAdapter
import com.example.kittyfacts.factList.FactsViewModel
import com.example.kittyfacts.factList.setupListAdapter
import com.example.kittyfacts.util.EventObserver
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FactsFragment : Fragment() {

    val factsViewModel : FactsViewModel by sharedViewModel()

    lateinit var viewDataBinding: FragmentFactsBinding

    lateinit var listAdapter: FactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding = FragmentFactsBinding.inflate(inflater, container, false).apply {
            viewmodel = factsViewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        factsViewModel.openDetailsEvent.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(R.id.action_FactsFragment_to_DetailFragment)
        })
    }
}