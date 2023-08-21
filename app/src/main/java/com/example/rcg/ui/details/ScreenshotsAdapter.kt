package com.example.rcg.ui.details

import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.rcg.R
import com.example.rcg.databinding.ItemScreenshotBinding
import com.example.rcg.model.base.ListItem
import com.example.rcg.ui.base.model.ScreenshotItem
import com.example.rcg.ui.base.model.game.ScreenshotPlaceholderItem
import com.example.rcg.util.release
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class ScreenshotsAdapter(glide: RequestManager) : ListDelegationAdapter<List<ListItem>>() {

    init {
        delegatesManager
            .addDelegate(screenshotDelegate(glide))
            .addDelegate(screenshotPlaceholderDelegate())
    }

    private fun screenshotDelegate(glide: RequestManager) =
        adapterDelegateViewBinding<ScreenshotItem, ListItem, ItemScreenshotBinding>(
            { inflater, container -> ItemScreenshotBinding.inflate(inflater, container, false) }
        ) {
            bind {
                val resources = binding.root.resources
                glide.load(item.image)
                    .override(
                        resources.getDimensionPixelOffset(R.dimen.game_card_wide_width),
                        resources.getDimensionPixelOffset(R.dimen.game_card_wide_high)
                    )
                    .transform(
                        CenterCrop(),
                        RoundedCorners(resources.getDimensionPixelOffset(R.dimen.game_card_radius))
                    )
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.bg_item_placeholder)
                    .into(binding.imageView)
            }

            onViewRecycled { glide.release(binding.imageView) }
        }

    private fun screenshotPlaceholderDelegate() =
        adapterDelegateViewBinding<ScreenshotPlaceholderItem, ListItem, ItemScreenshotBinding>(
            { inflater, container -> ItemScreenshotBinding.inflate(inflater, container, false) }
        ) {
            bind {
                binding.imageView.setBackgroundResource(R.drawable.bg_item_placeholder)
            }
        }
}