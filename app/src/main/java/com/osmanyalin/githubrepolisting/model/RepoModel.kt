package com.osmanyalin.githubrepolisting.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoModel(
    val name: String,
    val stargazers_count: Int,
    val open_issue_count: Int,
    val owner: OwnerModel
): Parcelable