package com.osmanyalin.githubrepolisting.ui.repolist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.osmanyalin.githubrepolisting.R
import com.osmanyalin.githubrepolisting.base.BaseFragment
import com.osmanyalin.githubrepolisting.databinding.FragmentRepoListBinding
import com.osmanyalin.githubrepolisting.model.RepoModel
import com.osmanyalin.githubrepolisting.network.Resource
import com.osmanyalin.githubrepolisting.ui.navigation.NavigationManager
import com.osmanyalin.githubrepolisting.ui.repodetail.RepoDetailFragment
import com.osmanyalin.githubrepolisting.ui.shared.SharedRepoViewModel
import com.osmanyalin.githubrepolisting.ui.toolbar.FragmentToolbar
import com.osmanyalin.githubrepolisting.util.Constant
import com.osmanyalin.githubrepolisting.util.disable
import com.osmanyalin.githubrepolisting.util.enable
import com.osmanyalin.githubrepolisting.util.hideKeyboard
import com.osmanyalin.githubrepolisting.view.ScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : BaseFragment(), RepoListView, View.OnClickListener, TextWatcher {

    private lateinit var binding: FragmentRepoListBinding
    private val viewModel: SharedRepoViewModel by activityViewModels()

    private lateinit var adapter: RepoListAdapter
    private var username: String = ""
    private var page: Int = 1
    private var isAllDataLoaded: Boolean = false

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
        handleRecyclerView()
        binding.etUserName.addTextChangedListener(this)
        binding.btnSubmit.setOnClickListener(this)
        binding.btnSubmit.disable()
        observeFavoriteList()
    }

    private fun handleRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvRepoList.layoutManager = linearLayoutManager
        binding.rvRepoList.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        binding.rvRepoList.addOnScrollListener(object : ScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                if(!isAllDataLoaded) {
                    page++
                    getUserRepos(username, page.toString())
                }
            }
        })
        adapter = RepoListAdapter()
        binding.rvRepoList.adapter = adapter
    }

    private fun getUserRepos(username: String, page: String = Constant.INITIAL_PAGE_QUERY) {
        viewModel.getUserRepos(username, page).observe(viewLifecycleOwner, {

            when (it.status) {
                Resource.Status.SUCCESS -> onSuccess(it.data)
                Resource.Status.ERROR -> onError()
                Resource.Status.LOADING -> onLoading()
            }
        })
    }

    private fun observeFavoriteList() {
        viewModel.allFavorites.observe(this, {
            if(it.isNotEmpty() && ::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onSuccess(data: List<RepoModel>?) {
        hideLoading()
        binding.progressBottom.visibility = View.GONE
        binding.btnSubmit.enable()

        if(data.isNullOrEmpty() && adapter.itemCount == 0) {
            binding.noDataView.visibility = View.VISIBLE
        } else {
            binding.noDataView.visibility = View.GONE
        }

        data?.toMutableList()?.let {
            if(it.size > 0) {
                adapter.addItems(it)
                adapter.onItemClick { repo -> navigateToDetail(repo.id) }
            } else {
                isAllDataLoaded = true
            }
        }
    }

    override fun onError() {
        hideLoading()
        binding.progressBottom.visibility = View.GONE
        binding.btnSubmit.enable()
        showToast(R.string.an_error_occurred)
        isAllDataLoaded = true
    }

    override fun onLoading() {
        if(adapter.itemCount > 0) {
            binding.progressBottom.visibility = View.VISIBLE
        } else {
            showLoading()
        }

        binding.btnSubmit.disable()
        binding.noDataView.visibility = View.GONE
    }

    override fun onSubmit() {
        hideKeyboard()
        adapter.clear()
        binding.etUserName.clearFocus()
        isAllDataLoaded = false
        page = 1
        getUserRepos(username)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.btnSubmit.id -> onSubmit()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        username = s.toString()

        if(username.isEmpty()) {
            binding.btnSubmit.disable()
        } else {
            binding.btnSubmit.enable()
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    private fun navigateToDetail(repoId: Int) {
        hideKeyboard()
        navigationManager.openFragment(RepoDetailFragment.newInstance(repoId), transaction = NavigationManager.Transaction.ADD)
    }
}