package com.mywings.smartcarlock

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.mywings.smartcarlock.process.OnRegistrationListener
import com.mywings.smartcarlock.process.ProgressDialogUtil
import com.mywings.smartcarlock.process.RegistrationAsync
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONObject

class RegistrationActivity : AppCompatActivity(), OnRegistrationListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        progressDialogUtil = ProgressDialogUtil(this)
        btnSignUp.setOnClickListener {

            if (validate()) {
                init()
            } else {
                val snackbar = Snackbar.make(btnSignUp, "Fill mandatory fields", Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Ok") {
                }
                snackbar.show()
            }

        }
    }

    private fun validate(): Boolean {
        if (txtName.text.isNullOrEmpty() || txtEmail.text.isNullOrEmpty() || txtPhone.text.isNullOrEmpty() || txtPassword.text.isNullOrEmpty()) {
            return false
        }
        return true
    }

    private fun init() {
        progressDialogUtil.show()
        val registrationAsync = RegistrationAsync()
        var request = JSONObject()
        var param = JSONObject()
        param.put("Name", txtName.text)
        param.put("Email", txtEmail.text)
        param.put("Phone", txtPhone.text)
        param.put("Phone0", txtPhoneNumber0.text)
        param.put("Phone00", txtPhoneNumber00.text)
        param.put("Password", txtPassword.text)

        request.put("request", param)
        registrationAsync.setOnRegistrationListener(this, request)
    }


    override fun onRegistrationComplete(result: String?) {
        progressDialogUtil.hide()
        if (!result.isNullOrEmpty()) {
            val snackbar = Snackbar.make(btnSignUp, "Registration completed.", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Ok") {
                finish()
            }
            snackbar.show()
        } else {
            val snackbar = Snackbar.make(btnSignUp, "An error has occurred.", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Ok") {
            }
            snackbar.show()
        }

    }
}
