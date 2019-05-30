package com.mywings.smartcarlock

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.mywings.messmanagementsystem.process.OnSendOptionListener
import com.mywings.messmanagementsystem.process.SendOtpAsync
import com.mywings.smartcarlock.process.OnRegistrationListener
import com.mywings.smartcarlock.process.ProgressDialogUtil
import com.mywings.smartcarlock.process.RegistrationAsync
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONObject

class RegistrationActivity : AppCompatActivity(), OnRegistrationListener, OnSendOptionListener {

    private lateinit var progressDialogUtil: ProgressDialogUtil
    private lateinit var number: String
    private lateinit var input: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        progressDialogUtil = ProgressDialogUtil(this)
        btnSignUp.setOnClickListener {

            if (validate()) {
                //
                // init()
                initotp();
            } else {
                val snackbar = Snackbar.make(btnSignUp, "Fill mandatory fields", Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction("Ok") {
                }
                snackbar.show()
            }

        }
    }

    private fun validate(): Boolean {
        if (txtName.text.isNullOrEmpty() || txtEmail.text.isNullOrEmpty() || txtPhone.text.isNullOrEmpty() || txtPhone.text.toString().length != 10 || txtPassword.text.isNullOrEmpty()) {
            return false
        }

        /* if (!txtPhone.text.toString().startsWith("7") || !txtPhone.text.toString().startsWith("8") || !txtPhone.text.toString().startsWith(
                 "9"
             )
         ) {
             return false
         }*/


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

    private fun initotp() {
        progressDialogUtil.show()
        number = getRandomNumberString()
        input =
            "https://api.textlocal.in/send/?apiKey=wnl6P220GB4-NLTbWCaPwzfFPHRoSBz16bgyFjAsie&sender=TXTLCL&numbers=${txtPhone.text}&message=${number}"
        val sendOtp = SendOtpAsync()
        sendOtp.setSendOtpListener(this, input)
    }

    private fun getRandomNumberString(): String {
        //val rnd = Random(200000)
        //val number = rnd.nextInt(999999)
        //return String.format("%06d", number)

        val rnds = (100000..999999).random()

        return rnds.toString()
    }


    private fun show() {
        val li = LayoutInflater.from(this)
        val promptsView = li.inflate(R.layout.layout_prompt, null)
        val alertDialogBuilder = AlertDialog.Builder(
            this
        )
        alertDialogBuilder.setView(promptsView)
        val userInput = promptsView
            .findViewById(R.id.editTextDialogUserInput) as EditText
        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { dialog, id ->
                if (userInput.text.toString() == number) {
                    //initRegistration()
                    init()
                } else {
                    Toast.makeText(this, "Enter valid otp", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog, id -> dialog.cancel() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    override fun onRegistrationComplete(result: String?) {
        progressDialogUtil.hide()
        if (!result.isNullOrEmpty() && result.equals("1", true)) {
            val snackbar = Snackbar.make(btnSignUp, "Registration completed.", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Ok") {
                finish()
            }
            snackbar.show()
        } else if (!result.isNullOrEmpty() && result.equals("3", true)) {
            val snackbar = Snackbar.make(btnSignUp, "Already exists", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Ok") {
            }
            snackbar.show()
        } else {
            val snackbar = Snackbar.make(btnSignUp, "An error has occurred.", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Ok") {
            }
            snackbar.show()
        }

    }

    override fun otpSent(result: String?) {
        progressDialogUtil.hide()
        if (result!!.contains("error")) {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
        } else {
            show()
        }
    }
}
