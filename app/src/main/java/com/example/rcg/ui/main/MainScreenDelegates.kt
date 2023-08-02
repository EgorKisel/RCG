package com.example.rcg.ui.main

import com.example.rcg.databinding.ItemGameThinBinding
import com.example.rcg.databinding.ItemGameWideBinding
import com.example.rcg.databinding.ItemGamesHorizontalBinding
import com.example.rcg.databinding.ItemProgressThinBinding
import com.example.rcg.databinding.ItemProgressWideBinding
import com.example.rcg.model.base.ListItem
import com.example.rcg.model.game.GameThinItem
import com.example.rcg.model.game.GameWideItem
import com.example.rcg.model.game.GamesHorizontalItem
import com.example.rcg.model.game.ProgressThinItem
import com.example.rcg.model.game.ProgressWideItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object MainScreenDelegates {

    val gamesHorizontalDelegate =
        adapterDelegateViewBinding<GamesHorizontalItem, ListItem, ItemGamesHorizontalBinding>(
            { inflater, container ->
                ItemGamesHorizontalBinding.inflate(
                    inflater,
                    container,
                    false
                ).apply {
                    recyclerView.adapter = ListDelegationAdapter(
                        wideGameDelegate,
                        thinGameDelegate,
                        wideProgressDelegate,
                        thinProgressDelegate
                    )
                }
            }
        ) {
            bind {
                binding.titleTextView.text = item.title
                (binding.recyclerView.adapter as ListDelegationAdapter<List<ListItem>>).apply {
                    items = item.games
                    notifyDataSetChanged()
                }
            }
        }

    private val wideProgressDelegate =
        adapterDelegateViewBinding<ProgressWideItem, ListItem, ItemProgressWideBinding>(
            { inflater, container ->
                ItemProgressWideBinding.inflate(
                    inflater,
                    container,
                    false
                )
            }
        ) {}

    private val wideGameDelegate =
        adapterDelegateViewBinding<GameWideItem, ListItem, ItemGameWideBinding>(
            { inflater, container ->
                ItemGameWideBinding.inflate(
                    inflater,
                    container,
                    false
                )
            }
        ) {
            bind {
                binding.imageView.setBackgroundColor(item.hashCode())
                binding.title = item.title
                binding.executePendingBindings()
            }
        }

    private val thinProgressDelegate =
        adapterDelegateViewBinding<ProgressThinItem, ListItem, ItemProgressThinBinding>(
            { inflater, container ->
                ItemProgressThinBinding.inflate(
                    inflater,
                    container,
                    false
                )
            }
        ) {}

    private val thinGameDelegate =
        adapterDelegateViewBinding<GameThinItem, ListItem, ItemGameThinBinding>(
            { inflater, container ->
                ItemGameThinBinding.inflate(
                    inflater,
                    container,
                    false
                )
            }
        ) {
            bind {
                binding.imageView.setBackgroundColor(item.hashCode())
                binding.title = item.title
                binding.executePendingBindings()
            }
        }
}