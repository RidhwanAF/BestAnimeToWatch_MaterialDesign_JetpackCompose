package com.raf.topanime.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Anime(
    @DrawableRes val imageRes: Int,
    @StringRes val titleRes : Int,
    @StringRes val descRes: Int,
    val topText: String = "Top #"
)