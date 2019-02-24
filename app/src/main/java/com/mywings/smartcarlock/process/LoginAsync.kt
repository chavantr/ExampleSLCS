package com.mywings.smartcarlock.process

import android.os.AsyncTask
import com.mywings.smartcarlock.model.User
import org.json.JSONObject
import java.net.CacheRequest

class LoginAsync : AsyncTask<JSONObject, Void, User?>() {

    private val httpConnectionUtil = HttpConnectionUtil()

    private lateinit var onLoginListener: OnLoginListener

    override fun doInBackground(vararg param: JSONObject?): User? {

        val response = httpConnectionUtil.requestPost("", param[0])
        val jUser = JSONObject(response)
        var user = User()
        user.id = jUser.getInt("Id")
        user.name = jUser.getString("Name")
        user.email = jUser.getString("Email")
        user.password = jUser.getString("Password")
        user.phone = jUser.getString("Phone")
        user.phone0 = jUser.getString("Phone0")
        user.phone00 = jUser.getString("Phone00")
        return user;
    }

    override fun onPostExecute(result: User?) {
        super.onPostExecute(result)
        onLoginListener.onLoginSuccess(result)
    }

    fun setOnLoginListener(onLoginListener: OnLoginListener, request: JSONObject) {
        this.onLoginListener = onLoginListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }


}