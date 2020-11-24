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
import com.osmanyalin.githubrepolisting.base.BaseResponseListener
import com.osmanyalin.githubrepolisting.databinding.FragmentRepoListBinding
import com.osmanyalin.githubrepolisting.model.RepoModel
import com.osmanyalin.githubrepolisting.network.Resource
import com.osmanyalin.githubrepolisting.ui.navigation.NavigationManager
import com.osmanyalin.githubrepolisting.ui.repodetail.RepoDetailFragment
import com.osmanyalin.githubrepolisting.ui.toolbar.FragmentToolbar
import com.osmanyalin.githubrepolisting.util.disable
import com.osmanyalin.githubrepolisting.util.enable
import com.osmanyalin.githubrepolisting.util.hideKeyboard
import com.osmanyalin.githubrepolisting.view.ScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : BaseFragment(), BaseResponseListener<List<RepoModel>>, View.OnClickListener, TextWatcher {

    private lateinit var binding: FragmentRepoListBinding
    private val viewModel: RepoListViewModel by activityViewModels()

    private lateinit var adapter: RepoListAdapter
    private var username: String = ""

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
    }

    private fun handleRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvRepoList.layoutManager = linearLayoutManager
        binding.rvRepoList.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        binding.rvRepoList.addOnScrollListener(object : ScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                //TODO: handle it later if it is necessary
            }
        })
        adapter = RepoListAdapter()
        binding.rvRepoList.adapter = adapter
    }

    private fun getUserRepos(username: String) {
        viewModel.getUserRepos(username).observe(viewLifecycleOwner, {

            when (it.status) {
                Resource.Status.SUCCESS -> onSuccess(it.data)
                Resource.Status.ERROR -> onError()
                Resource.Status.LOADING -> onLoading()
            }
        })
    }

    override fun onSuccess(data: List<RepoModel>?) {
        hideLoading()
        binding.btnSubmit.enable()

        if(data.isNullOrEmpty()) {
            binding.noDataView.visibility = View.VISIBLE
        } else {
            binding.noDataView.visibility = View.GONE
        }

        val repoList = data?.toMutableList()
        repoList?.let {
            adapter.addItems(it)
            adapter.onClick { content -> navigateToDetail(content.id) }
        }
    }

    override fun onError() {
        hideLoading()
        binding.btnSubmit.enable()
        showToast(R.string.an_error_occurred)
    }

    override fun onLoading() {
        showLoading()
        binding.btnSubmit.disable()
        binding.noDataView.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.btnSubmit.id -> {
                hideKeyboard()
                adapter.clear()
                getUserRepos(username)
            }
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