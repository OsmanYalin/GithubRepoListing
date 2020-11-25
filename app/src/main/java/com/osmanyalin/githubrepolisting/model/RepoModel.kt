package com.osmanyalin.githubrepolisting.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoModel(
    val id: Int,
    val name: String,
    val stargazers_count: String,
    val open_issues_count: String,
    val owner: OwnerModel
): Parcelable {

    var isFavorite: Boolean = false
}