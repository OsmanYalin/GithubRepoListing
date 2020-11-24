package com.osmanyalin.githubrepolisting.ui.main

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.osmanyalin.githubrepolisting.R
import com.osmanyalin.githubrepolisting.databinding.ActivityMainBinding
import com.osmanyalin.githubrepolisting.ui.repolist.RepoListFragment
import com.osmanyalin.githubrepolisting.util.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isProgressHidedBefore = false
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragment()
    }

    private fun addFragment() {
        runOnUiThread {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, RepoListFragment.newInstance()).commit()
        }
    }

    internal fun showLoading() {
        isLoading = true
        isProgressHidedBefore = false
        // To avoid to show progress when data is retrieved fast
        // It will provide a smoother UX
        lifecycleScope.launch {
            delay(Constant.PROGRESS_DELAY_MILLIS)
            if (!isProgressHidedBefore) {
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    internal fun hideLoading() {
        isLoading = false
        isProgressHidedBefore = true
        binding.progressBar.visibility = View.GONE
    }
}