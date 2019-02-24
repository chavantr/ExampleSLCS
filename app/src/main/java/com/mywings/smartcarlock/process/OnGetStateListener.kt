package com.mywings.smartcarlock.process

import com.mywings.smartcarlock.model.State

interface OnGetStateListener {
    fun onGetStateSuccess(result: State?)
}