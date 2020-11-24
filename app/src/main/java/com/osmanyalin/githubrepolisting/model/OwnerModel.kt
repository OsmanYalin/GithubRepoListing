package com.osmanyalin.githubrepolisting.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OwnerModel(
    val login: String,
    val avatar_url: String
): Parcelable

