package com.mywings.smartcarlock

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mywings.smartcarlock.model.State
import com.mywings.smartcarlock.model.UserInfoHolder
import com.mywings.smartcarlock.process.GetStateAsync
import com.mywings.smartcarlock.process.OnGetStateListener
import com.mywings.smartcarlock.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_car_details.*

class CarDetailsActivity : AppCompatActivity(), OnGetStateListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)
        progressDialogUtil = ProgressDialogUtil(this)
        init()
    }

    private fun init() {
        progressDialogUtil.show()
        val getStateAsync = GetStateAsync()
        getStateAsync.setOnStateListener(this, UserInfoHolder.getInstance().selectedCar.id.toString())
    }


    override fun onGetStateSuccess(result: State?) {
        progressDialogUtil.hide()
        if (null != result) {
            lblLP.text = "Vehicle : ${UserInfoHolder.getInstance().selectedCar.lp}"
        }
    }


}
