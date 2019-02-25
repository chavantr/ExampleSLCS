package com.mywings.smartcarlock.process

import android.os.AsyncTask
import org.json.JSONObject

class UpdateStateAsync : AsyncTask<JSONObject, Void, String?>() {

    private val httpConnectionUtil = HttpConnectionUtil()

    private lateinit var onUpdateStateListener: OnUpdateStateListener

    override fun doInBackground(vararg param: JSONObject?): String? {
        return httpConnectionUtil.requestPost(Constants.URL + Constants.SET_STATE, param[0])
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        onUpdateStateListener.onUpdateStateSuccess(result)
    }

    fun setOnUpdateStateListener(onUpdateStateListener: OnUpdateStateListener, request: JSONObject) {
        this.onUpdateStateListener = onUpdateStateListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}