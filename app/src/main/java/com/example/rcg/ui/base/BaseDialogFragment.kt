package com.example.rcg.ui.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.rcg.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch

abstract class BaseDialogFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BaseBottomSheetDialogTheme)
    }

    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.skipCollapsed = true
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        observe(viewLifecycleOwner) { action.invoke(it) }
    }

    fun SharedFlow<Throwable>.connectErrorData() {
        lifecycleScope.launch {
            sample(1000L).collect {
                if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                    Toast.makeText(context, R.string.default_error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}