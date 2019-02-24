package com.mywings.smartcarlock.process

import android.os.AsyncTask
import org.json.JSONObject

class RegistrationAsync : AsyncTask<JSONObject, Void, String?>() {

    private val httpConnectionUtil = HttpConnectionUtil()

    private lateinit var onRegistrationListener: OnRegistrationListener

    override fun doInBackground(vararg param: JSONObject?): String? {
        return httpConnectionUtil.requestPost(Constants.URL + Constants.REGISTRATION, param[0])
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        onRegistrationListener.onRegistrationComplete(result)
    }

    fun setOnRegistrationListener(onRegistrationListener: OnRegistrationListener, request: JSONObject) {
        this.onRegistrationListener = onRegistrationListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}