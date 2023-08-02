package com.example.rcg.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rcg.R
import com.example.rcg.databinding.FragmentMainBinding
import com.example.rcg.model.base.ListItem
import com.example.rcg.ui.base.viewBinding
import com.example.rcg.viewmodel.main.MainScreenVewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding { FragmentMainBinding.bind(it) }
    private val viewModel by viewModels<MainScreenVewModel>()

    private val adapter = ListDelegationAdapter<List<ListItem>>(
        MainScreenDelegates.gamesHorizontalDelegate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            viewModel.data.observe(viewLifecycleOwner) {
                adapter.apply {
                    items = it
                    notifyDataSetChanged()
                }
            }
        }
    }
}