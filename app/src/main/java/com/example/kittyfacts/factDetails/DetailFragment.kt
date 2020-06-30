package com.example.kittyfacts.factDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.kittyfacts.R
import com.example.kittyfacts.FactsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DetailFragment : Fragment() {
    val factsViewModel : FactsViewModel by sharedViewModel()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factsViewModel.openDetailsEvent.observe(viewLifecycleOwner, Observer {
            details_textview.text = it.peekContent().factDescription
        })
    }
}