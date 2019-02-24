package com.mywings.smartcarlock.joint

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mywings.smartcarlock.R
import com.mywings.smartcarlock.listener.OnCarSelectedListener
import com.mywings.smartcarlock.model.Car
import kotlinx.android.synthetic.main.layout_car_row.view.*

class JointAdapter(lst: List<Car>) : RecyclerView.Adapter<JointAdapter.JointAdapterViewHolder>() {


    private val lstCar = lst;

    private lateinit var onCarSelectedListener: OnCarSelectedListener


    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): JointAdapterViewHolder {
        return JointAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_car_row,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = lstCar.size

    override fun onBindViewHolder(viewHolder: JointAdapterViewHolder, position: Int) {

        viewHolder.lblName.text = lstCar[position].lp

        viewHolder.lblName.setOnClickListener {

            onCarSelectedListener.onCarSelectedSuccess(lstCar[position])

        }

    }


    inner class JointAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val lblName = itemView.lblName

    }

    fun setOnCarSelectedListener(onCarSelectedListener: OnCarSelectedListener) {
        this.onCarSelectedListener = onCarSelectedListener
    }

}