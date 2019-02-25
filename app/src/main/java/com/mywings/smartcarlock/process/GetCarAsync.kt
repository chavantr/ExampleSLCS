package com.mywings.smartcarlock.process

import android.os.AsyncTask
import com.mywings.smartcarlock.model.Car
import org.json.JSONArray
import java.util.ArrayList

class GetCarAsync : AsyncTask<String, Void, List<Car>?>() {

    private val httpConnectionUtil = HttpConnectionUtil()

    private lateinit var onGetCarListener: OnGetCarListener

    override fun doInBackground(vararg param: String?): List<Car>? {

        val response = httpConnectionUtil.requestGet(Constants.URL + Constants.GET_VEHICLE + "?id=${param[0]}")

        val jCar = JSONArray(response)

        if (null != jCar) {

            var lstCar = ArrayList<Car>()

            for (i in 0..(jCar.length() - 1)) {
                val jNode = jCar.getJSONObject(i)
                var car = Car()
                car.id = jNode.getInt("Id")

                car.lp = jNode.getString("LP")

                car.chNumber = jNode.getString("CHNumber")

                car.model = jNode.getString("Model")

                car.bName = jNode.getString("BName")

                lstCar.add(car)
            }

            return lstCar;

        }

        return null


    }

    override fun onPostExecute(result: List<Car>?) {
        super.onPostExecute(result)
        onGetCarListener.onCarSuccess(result)
    }

    fun setOnGetCarListener(onGetCarListener: OnGetCarListener, request: String) {
        this.onGetCarListener = onGetCarListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}