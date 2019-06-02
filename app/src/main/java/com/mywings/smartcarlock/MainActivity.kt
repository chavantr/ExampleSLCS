package com.mywings.smartcarlock

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.mywings.smartcarlock.model.User
import com.mywings.smartcarlock.model.UserInfoHolder
import com.mywings.smartcarlock.process.LoginAsync
import com.mywings.smartcarlock.process.OnLoginListener
import com.mywings.smartcarlock.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), OnLoginListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialogUtil = ProgressDialogUtil(this)
        btnSignIn.setOnClickListener {
            if (txtUserName.text!!.isNotEmpty() && txtPassword.text!!.isNotEmpty()) {
                init()
            } else {
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_LONG).show()
            }
        }
        btnSignUp.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init() {
        progressDialogUtil.show()
        val loginAsync = LoginAsync()
        var request = JSONObject()
        var param = JSONObject()
        param.put("Phone", txtUserName.text)
        param.put("Password", txtPassword.text)
        request.put("request", param)
        loginAsync.setOnLoginListener(this, request)

    }

    override fun onLoginSuccess(user: User?) {
        progressDialogUtil.hide()
        if (null != user) {
            UserInfoHolder.getInstance().user = user
            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "An error has occurred", Toast.LENGTH_LONG).show()
        }

    }
}
