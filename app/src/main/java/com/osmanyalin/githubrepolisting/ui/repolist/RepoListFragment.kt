package com.osmanyalin.githubrepolisting.ui.repolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.osmanyalin.githubrepolisting.R
import com.osmanyalin.githubrepolisting.base.BaseFragment
import com.osmanyalin.githubrepolisting.databinding.FragmentRepoListBinding
import com.osmanyalin.githubrepolisting.network.Resource
import com.osmanyalin.githubrepolisting.ui.toolbar.FragmentToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : BaseFragment() {

    private lateinit var binding: FragmentRepoListBinding
    private val viewModel: RepoListViewModel by viewModels()

    companion object {
        fun newInstance() = RepoListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRepoListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun toolbarBuilder(): FragmentToolbar {
        return FragmentToolbar.Builder()
            .withId(R.id.toolbar)
            .withTitle(R.string.app_name)
            .build()
    }

    override fun setUp(view: View?) {
//        handleRecyclerView()

        getUserRepos("OsmanYalin")
    }

    private fun getUserRepos(username: String) {
        viewModel.getUserRepos(username).observe(viewLifecycleOwner, {

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    hideLoading()
//                    navigationManager.openFragment(RepoDetailFragment.newInstance())
                    if(it.data.isNullOrEmpty()) {
                        binding.noDataView.visibility = View.VISIBLE
    //                    handleNoDataState()
                    } else {
                        binding.noDataView.visibility = View.GONE

                    }
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    showToast(R.string.an_error_occurred)
                }

                Resource.Status.LOADING -> showLoading()
            }
        })
    }
}