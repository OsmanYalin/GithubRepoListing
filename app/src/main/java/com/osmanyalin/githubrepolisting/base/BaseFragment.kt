package com.osmanyalin.githubrepolisting.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.osmanyalin.githubrepolisting.ui.main.MainActivity
import com.osmanyalin.githubrepolisting.ui.navigation.NavigationManager
import com.osmanyalin.githubrepolisting.ui.toolbar.FragmentToolbar
import com.osmanyalin.githubrepolisting.ui.toolbar.ToolbarManager
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp(view)
        ToolbarManager(toolbarBuilder(), view).prepareToolbar()
    }

    protected abstract fun setUp(view: View?)

    protected abstract fun toolbarBuilder(): FragmentToolbar

    protected fun showLoading() {
        (activity as MainActivity).showLoading()
    }

    protected fun hideLoading() {
        (activity as MainActivity).hideLoading()
    }

    protected fun showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(activity, getString(message), duration).show()
    }
}