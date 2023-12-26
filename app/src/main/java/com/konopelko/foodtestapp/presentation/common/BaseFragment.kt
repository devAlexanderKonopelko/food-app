package com.konopelko.foodtestapp.presentation.common

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

abstract class BaseFragment<UI_STATE, FRAGMENT_EVENT, INTENT,
        VIEW_MODEL : BaseViewModel<UI_STATE, *, FRAGMENT_EVENT, INTENT>> : Fragment() {

    abstract val viewModel: VIEW_MODEL

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fragmentEvent.collect(::handleFragmentEvent)
            }
        }
    }

    protected abstract fun handleFragmentEvent(uiEvent: FRAGMENT_EVENT)
}