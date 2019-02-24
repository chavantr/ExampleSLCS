package com.mywings.smartcarlock.process

import android.os.AsyncTask
import org.json.JSONObject

class AddCarAsync : AsyncTask<JSONObject, Void, String?>() {

    private val httpConnectionUtil = HttpConnectionUtil()
    private lateinit var onAddCarListener: OnAddCarListener

    override fun doInBackground(vararg param: JSONObject?): String? {
        return httpConnectionUtil.requestPost("", param[0])
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        onAddCarListener.onAddCarSuccess(result)
    }

    fun setOnAddCarListener(onAddCarListener: OnAddCarListener, request: JSONObject) {
        this.onAddCarListener = onAddCarListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}