package com.mywings.smartcarlock

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.mywings.smartcarlock.model.State
import com.mywings.smartcarlock.model.UserInfoHolder
import com.mywings.smartcarlock.process.*
import kotlinx.android.synthetic.main.activity_car_details.*
import org.json.JSONObject

class CarDetailsActivity : AppCompatActivity(), OnGetStateListener, OnUpdateStateListener {


    private lateinit var progressDialogUtil: ProgressDialogUtil

    private var id: Int = 0


    private var state = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)
        progressDialogUtil = ProgressDialogUtil(this)

        tglLock.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                state = checked
            } else {
                state = checked
            }

            initUpdate()

        }

        init()
    }

    private fun init() {
        progressDialogUtil.show()
        val getStateAsync = GetStateAsync()
        getStateAsync.setOnStateListener(this, UserInfoHolder.getInstance().selectedCar.id.toString())
    }


    private fun initUpdate() {
        progressDialogUtil.show()
        val updateStateAsync = UpdateStateAsync()
        var request = JSONObject()
        var param = JSONObject()
        param.put("State", state)
        param.put("Id", id)
        request.put("request", param)
        updateStateAsync.setOnUpdateStateListener(this, request)
    }


    override fun onGetStateSuccess(result: State?) {
        progressDialogUtil.hide()
        if (null != result) {
            id = result.id
            lblLP.text = "Vehicle : ${UserInfoHolder.getInstance().selectedCar.lp}"
            tglLock.isChecked = result.state.equals("true", true)
        }
    }

    override fun onUpdateStateSuccess(result: String?) {
        progressDialogUtil.hide()
        if (!result.isNullOrBlank()) {

            Toast.makeText(this@CarDetailsActivity, "Updated", Toast.LENGTH_LONG).show()
        }
    }


}
