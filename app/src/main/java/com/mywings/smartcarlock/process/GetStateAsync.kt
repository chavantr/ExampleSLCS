package com.mywings.smartcarlock.process

import android.os.AsyncTask
import com.mywings.smartcarlock.model.State
import org.json.JSONObject

class GetStateAsync : AsyncTask<String, Void, State>() {

    private val httpConnectionUtil = HttpConnectionUtil()

    private lateinit var onGetStateListener: OnGetStateListener


    override fun doInBackground(vararg param: String?): State {

        val response = httpConnectionUtil.requestGet(
            Constants.URL + Constants.GET_STATE + "?id=${param[0]}"
        )

        val jState = JSONObject(response)

        var state = State()

        state.id = jState.getInt("Id")

        state.lat = jState.getString("Lat")

        state.lng = jState.getString("Lat")

        state.state = jState.getString("States")

        state.vid = jState.getInt("VId")

        return state

    }

    override fun onPostExecute(result: State?) {
        super.onPostExecute(result)
        onGetStateListener.onGetStateSuccess(result)
    }

    fun setOnStateListener(onGetStateListener: OnGetStateListener, request: String) {
        this.onGetStateListener = onGetStateListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}