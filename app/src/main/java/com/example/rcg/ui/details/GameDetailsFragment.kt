package com.example.rcg.ui.details

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.rcg.R
import com.example.rcg.databinding.FragmentGameDetailsBinding
import com.example.rcg.ui.base.BaseDialogFragment
import com.example.rcg.ui.base.viewBinding
import com.example.rcg.util.getScreenSize
import com.example.rcg.util.onClick
import com.example.rcg.viewmodel.details.GameDetailsComponent
import com.example.rcg.viewmodel.details.GameDetailsScreenModel
import com.example.rcg.viewmodel.details.GameDetailsViewModel

class GameDetailsFragment : BaseDialogFragment() {

    private val binding by viewBinding { FragmentGameDetailsBinding.bind(it) }
    private val args by navArgs<GameDetailsFragmentArgs>()
    private val component: GameDetailsComponent by lazy {
        GameDetailsComponent.create(GameDetailsScreenModel.from(args))
    }
    private val viewModel by viewModels<GameDetailsViewModel> { component.viewModelFactory() }
    private val adapter by lazy { ScreenshotsAdapter(glide) }
    private val glide by lazy { Glide.with(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.errorData.connectErrorData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_game_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            screenshotsRecyclerView.adapter = adapter
            refreshImageView.onClick { viewModel.refresh() }
            tryAgainTextView.onClick { viewModel.refresh() }

            viewModel.data.observe {
                titleTextView.text = it.title
                refreshImageView.isVisible = false
                tryAgainTextView.isVisible = false

                val radius = resources.getDimensionPixelOffset(R.dimen.game_card_radius).toFloat()
                val screenSize = getScreenSize(requireContext())
                glide.load(it.image)
                    .override(screenSize.first, (screenSize.first * 9f / 16).toInt())
                    .transform(CenterCrop(), GranularRoundedCorners(0f, 0f, radius, radius))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.bg_item_placeholder)
                    .into(coverImageView)

                if (it is GameDetailsScreenModel.Content) {
                    descriptionTextView.text = Html.fromHtml(it.description)
                    adapter.items = it.screenshots
                } else if (it is GameDetailsScreenModel.Error) {
                    refreshImageView.isInvisible = true
                    tryAgainTextView.isInvisible = true
                }
            }
        }
    }
}