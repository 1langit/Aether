package com.pkmaether.aether.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val date: String,
    val author: String,
    val source: String,
    val sourceUrl: String,
    val imageUrl: String,
    val content: String,
): Parcelable
