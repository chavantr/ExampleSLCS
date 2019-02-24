package com.mywings.smartcarlock.listener

import com.mywings.smartcarlock.model.Car

interface OnCarSelectedListener {
    fun onCarSelectedSuccess(car: Car)
}