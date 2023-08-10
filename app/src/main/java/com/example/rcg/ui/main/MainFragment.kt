package com.example.rcg.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rcg.DI
import com.example.rcg.R
import com.example.rcg.databinding.FragmentMainBinding
import com.example.rcg.ui.base.viewBinding
import com.example.rcg.viewmodel.main.DaggerMainScreenComponent
import com.example.rcg.viewmodel.main.MainScreenComponent
import com.example.rcg.viewmodel.main.MainScreenVewModel

class MainFragment : Fragment(R.layout.fragment_main) {
    private val component by lazy { MainScreenComponent.create() }

    private val binding by viewBinding { FragmentMainBinding.bind(it) }
    private val viewModel by viewModels<MainScreenVewModel> { component.viewModelFactory() }

    private val adapter by lazy { MainScreenAdapter(
        onItemBind = viewModel::initCategory,
        onReadyToLoadMore = viewModel::readyToLoadMore
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            viewModel.data.observe(viewLifecycleOwner) {
                adapter.items = it
            }
        }
    }

//    companion object {
//        fun create(component: MainScreenComponent) = MainFragment().apply {
//            this.component = component
//        }
//    }
}