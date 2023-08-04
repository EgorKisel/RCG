package com.example.rcg.ui.main

import android.app.Activity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.rcg.R
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
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object MainScreenDelegates {

    fun gamesHorizontalDelegate() =
        adapterDelegateViewBinding<GamesHorizontalItem, ListItem, ItemGamesHorizontalBinding>(
            { inflater, container ->
                ItemGamesHorizontalBinding.inflate(
                    inflater,
                    container,
                    false
                )
            }
        ) {
            // onCreateViewHolder
            val adapter = GameCardsAdapter()
            binding.recyclerView.adapter = adapter

            // oNBindViewHolder
            bind {
                binding.titleTextView.text = item.title
                adapter.items = item.games
            }
        }

    fun wideProgressDelegate() =
        adapterDelegateViewBinding<ProgressWideItem, ListItem, ItemProgressWideBinding>(
            { inflater, container ->
                ItemProgressWideBinding.inflate(
                    inflater,
                    container,
                    false
                )
            }
        ) {}

    fun wideGameDelegate() =
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
                val resources = binding.root.resources
                Glide.with(binding.root)
                    .load(item.image)
                    .override(
                        resources.getDimensionPixelOffset(R.dimen.game_card_wide_width),
                        resources.getDimensionPixelOffset(R.dimen.game_card_wide_high)
                    )
                    .transform(
                        CenterCrop(),
                        RoundedCorners(resources.getDimensionPixelOffset(R.dimen.game_card_radius))
                    )
                    .transition(withCrossFade())
                    .into(binding.imageView)
                binding.title = item.title
                binding.executePendingBindings()
            }
            onViewRecycled {
                if ((binding.root.context as? Activity)?.isDestroyed?.not() == true) {
                    Glide.with(binding.root).clear(binding.imageView)
                }
            }
        }

    fun thinProgressDelegate() =
        adapterDelegateViewBinding<ProgressThinItem, ListItem, ItemProgressThinBinding>(
            { inflater, container ->
                ItemProgressThinBinding.inflate(
                    inflater,
                    container,
                    false
                )
            }
        ) {}

    fun thinGameDelegate() =
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
                val resources = binding.root.resources
                Glide.with(binding.root)
                    .load(item.image)
                    .override(
                        resources.getDimensionPixelOffset(R.dimen.game_card_thin_width),
                        resources.getDimensionPixelOffset(R.dimen.game_card_thin_high)
                    )
                    .transform(
                        CenterCrop(),
                        RoundedCorners(resources.getDimensionPixelOffset(R.dimen.game_card_radius))
                    )
                    .transition(withCrossFade())
                    .into(binding.imageView)
                binding.title = item.title
                binding.executePendingBindings()
            }
            onViewRecycled {
                if ((binding.root.context as? Activity)?.isDestroyed?.not() == true) {
                    Glide.with(binding.root).clear(binding.imageView)
                }
            }
        }
}