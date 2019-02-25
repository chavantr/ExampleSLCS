package com.mywings.smartcarlock

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.mywings.smartcarlock.model.UserInfoHolder
import com.mywings.smartcarlock.process.AddCarAsync
import com.mywings.smartcarlock.process.HttpConnectionUtil
import com.mywings.smartcarlock.process.OnAddCarListener
import com.mywings.smartcarlock.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_add_car.*
import org.json.JSONObject

class AddCarActivity : AppCompatActivity(), OnAddCarListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        progressDialogUtil = ProgressDialogUtil(this)
        btnSave.setOnClickListener {

            if (validate()) {
                init()
            } else {

                val snackbar = Snackbar.make(btnSave, "Enter fields", Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Ok") {

                }
                snackbar.show()

            }

        }
    }

    private fun init() {
        progressDialogUtil.show()
        val addCarAsync = AddCarAsync()
        var request = JSONObject()
        var param = JSONObject()
        param.put("LP", txtLP.text)
        param.put("LP", txtLP.text)
        param.put("CHNumber", txtCH.text)
        param.put("Model", txtModel.text)
        param.put("BName", txtbName.text)
        param.put("VId", UserInfoHolder.getInstance().user.id)
        request.put("request", param)
        addCarAsync.setOnAddCarListener(this, request)
    }

    private fun validate(): Boolean {

        if (txtCH.text.isNullOrEmpty() || txtLP.text.isNullOrEmpty() || txtModel.text.isNullOrEmpty() || txtbName.text.isNullOrEmpty()) {
            return false
        }


        return true
    }

    override fun onAddCarSuccess(inserted: String?) {
        progressDialogUtil.hide()
        if (!inserted.isNullOrEmpty()) {
            val snackbar = Snackbar.make(btnSave, "Vehicle added.", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Ok") {
                finish()
            }
            snackbar.show()
        } else {
            val snackbar = Snackbar.make(btnSave, "An error has occurred", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Ok") {

            }
            snackbar.show()
        }

    }
}
