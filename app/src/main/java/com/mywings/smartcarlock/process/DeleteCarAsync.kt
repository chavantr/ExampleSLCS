package com.mywings.smartcarlock.process

import android.os.AsyncTask

class DeleteCarAsync : AsyncTask<String?, Void, String?>() {

    private lateinit var onCarDeleteListener: OnCarDeleteListener

    override fun doInBackground(vararg param: String?): String? {
        return HttpConnectionUtil().requestGet(Constants.URL + Constants.DELETE_CAR + param[0])
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        onCarDeleteListener.onDeleteSuccess(result)
    }

    fun setOnCarDeleteListener(onCarDeleteListener: OnCarDeleteListener, request: String?) {
        this.onCarDeleteListener = onCarDeleteListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}