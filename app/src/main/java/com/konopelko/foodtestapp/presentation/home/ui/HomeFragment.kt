package com.konopelko.foodtestapp.presentation.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.konopelko.foodtestapp.presentation.common.BaseFragment
import com.konopelko.foodtestapp.presentation.common.extension.createContentView
import com.konopelko.foodtestapp.presentation.home.HomeFragmentEvent
import com.konopelko.foodtestapp.presentation.home.HomeFragmentEvent.ShowFoodName
import com.konopelko.foodtestapp.presentation.home.HomeFragmentEvent.ShowLoadingError
import com.konopelko.foodtestapp.presentation.home.HomeIntent
import com.konopelko.foodtestapp.presentation.home.HomeUiState
import com.konopelko.foodtestapp.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeUiState, HomeFragmentEvent, HomeIntent, HomeViewModel>() {

    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createContentView {
        HomeScreen(viewModel = viewModel)
    }

    override fun handleFragmentEvent(uiEvent: HomeFragmentEvent) = when (uiEvent) {
        is ShowFoodName -> showSnackbar(uiEvent.name)
        is ShowLoadingError -> showSnackbar(
            text = uiEvent.errorMessage,
            duration = Snackbar.LENGTH_LONG
        )
    }

    private fun showSnackbar(
        text: String,
        duration: Int = Snackbar.LENGTH_SHORT
    ) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            text,
            duration
        ).show()
    }
}