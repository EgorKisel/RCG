package com.example.rcg.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.rcg.R
import com.example.rcg.databinding.FragmentMainBinding
import com.example.rcg.ui.base.ListItem
import com.example.rcg.ui.base.viewBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding { FragmentMainBinding.bind(it) }

    private val adapter = ListDelegationAdapter<List<ListItem>>(
        MainScreenDelegates.gamesHorizontalDelegate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            adapter.apply {
                items = listOf(
                    GamesHorizontalItem(
                        title = "Wide games title",
                        games = IntRange(1, 20).map { GameWideItem(it.toLong(), "Game title $it") }
                    ),
                    GamesHorizontalItem(
                        title = "Thin games title",
                        games = IntRange(1, 20).map { GameThinItem(it.toLong(), "Game title $it") }
                    ),
                    GamesHorizontalItem(
                        title = "Wide games title",
                        games = IntRange(1, 20).map { GameWideItem(it.toLong(), "Game title $it") }
                    )
                )
                notifyDataSetChanged()
            }
        }
    }
}