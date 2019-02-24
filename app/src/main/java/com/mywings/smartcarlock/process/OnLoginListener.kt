package com.mywings.smartcarlock.process

import com.mywings.smartcarlock.model.User

interface OnLoginListener {
    fun onLoginSuccess(user: User?)
}