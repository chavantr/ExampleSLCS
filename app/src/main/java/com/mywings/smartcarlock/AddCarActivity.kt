package com.mywings.smartcarlock

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.mywings.smartcarlock.model.UserInfoHolder
import com.mywings.smartcarlock.process.AddCarAsync
import com.mywings.smartcarlock.process.OnAddCarListener
import com.mywings.smartcarlock.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_add_car.*
import org.json.JSONObject

class AddCarActivity : AppCompatActivity(), OnAddCarListener {


    private lateinit var progressDialogUtil: ProgressDialogUtil

    private val lst = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)


        lst["ABCDEFGHIJK12345"] = "8983553593"
        lst["ABCDEF12345GHIJK"] = "7020006394"
        lst["ABC12345DEFGHIJK"] = "7020006394"
        lst["ABC1234DEFGHIJK5"] = "8551010432"
        lst["ABC1235DEFGHIJK4"] = "9762904612"
        lst["ABC1245DEFGHIJK3"] = "8208008652"



        progressDialogUtil = ProgressDialogUtil(this)
        btnSave.setOnClickListener {


            val strCH = lst[txtCH.text.toString()]

            if (strCH != UserInfoHolder.getInstance().user.phone0) {
                Toast.makeText(
                    this@AddCarActivity,
                    "Mobile number did not match, please enter correct chesis number",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
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

        if (txtCH.text.isNullOrEmpty() || txtCH.text.toString().length < 16 || txtLP.text.isNullOrEmpty() || txtModel.text.isNullOrEmpty() || txtbName.text.isNullOrEmpty()) {
            return false
        }

        return true
    }

    override fun onAddCarSuccess(inserted: String?) {
        progressDialogUtil.hide()
        if (!inserted.isNullOrEmpty() && inserted.equals("1", true)) {
            val snackbar = Snackbar.make(btnSave, "Vehicle added.", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Ok") {
                finish()
            }
            snackbar.show()
        } else if (!inserted.isNullOrEmpty() && inserted.equals("3", true)) {
            val snackbar = Snackbar.make(btnSave, "Already exists", Snackbar.LENGTH_INDEFINITE)
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
