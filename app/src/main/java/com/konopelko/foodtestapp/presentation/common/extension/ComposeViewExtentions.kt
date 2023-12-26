package com.konopelko.foodtestapp.presentation.common.extension

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import androidx.fragment.app.Fragment
import com.konopelko.foodtestapp.presentation.common.theme.FoodTestAppTheme

fun Fragment.createContentView(content: @Composable () -> Unit): View =
    ComposeView(requireContext()).setThemedContent(content)

fun ComposeView.setThemedContent(content: @Composable () -> Unit): View = apply {
    setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
    setContent { FoodTestAppTheme(content = content) }
}