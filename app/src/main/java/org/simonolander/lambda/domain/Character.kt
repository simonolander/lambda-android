package org.simonolander.lambda.domain

import androidx.annotation.DrawableRes
import org.simonolander.lambda.R

enum class Character(
    @DrawableRes
    val profilePicture: Int
){
    Lambert(R.drawable.lambert),
    Developer(R.drawable.simon),
}
