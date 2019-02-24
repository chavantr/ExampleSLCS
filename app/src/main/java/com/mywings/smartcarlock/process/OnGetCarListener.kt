package com.mywings.smartcarlock.process

import com.mywings.smartcarlock.model.Car

interface OnGetCarListener {
    fun onCarSuccess(result: List<Car>?)
}