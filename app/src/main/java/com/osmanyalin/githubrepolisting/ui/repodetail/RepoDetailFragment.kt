package com.osmanyalin.githubrepolisting.ui.repodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.osmanyalin.githubrepolisting.R
import com.osmanyalin.githubrepolisting.base.BaseFragment
import com.osmanyalin.githubrepolisting.databinding.FragmentRepoDetailBinding
import com.osmanyalin.githubrepolisting.ui.repolist.RepoListViewModel
import com.osmanyalin.githubrepolisting.ui.toolbar.FragmentToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentRepoDetailBinding
    private val viewModel: RepoListViewModel by activityViewModels()
    private val repoId by lazy { (arguments?.getInt(ARG_REPO_ID) ?: 0) }

    companion object {

        const val ARG_REPO_ID = "ARG_REPO_ID"

        fun newInstance(repoId: Int): RepoDetailFragment = RepoDetailFragment().apply {
            arguments = Bundle().apply { putInt(ARG_REPO_ID, repoId) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRepoDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun setUp(view: View?) {
        viewModel.fetchRepoDetail(repoId)
        bindContent()
    }

    override fun toolbarBuilder(): FragmentToolbar {
        val onBackPressListener = View.OnClickListener {
            activity?.onBackPressed()
            return@OnClickListener
        }

        return FragmentToolbar.Builder()
            .withId(R.id.toolbar)
            .withTitle(R.string.repo_detail)
            .onBackPressed(onBackPressListener)
            .build()
    }

    private fun bindContent() {
        viewModel.repoItem.observe(this, {
            binding.run {
                repoModel = it
                ivUser.load(it.owner.avatar_url)
            }
        })
    }
}