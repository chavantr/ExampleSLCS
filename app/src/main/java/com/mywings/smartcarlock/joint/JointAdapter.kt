package com.mywings.smartcarlock.joint

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mywings.smartcarlock.R
import com.mywings.smartcarlock.listener.OnCarSelectedListener
import com.mywings.smartcarlock.model.Car
import com.mywings.smartcarlock.process.DeleteCarAsync
import com.mywings.smartcarlock.process.OnCarDeleteListener
import com.mywings.smartcarlock.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.layout_car_row.view.*

class JointAdapter(lst: List<Car>) : RecyclerView.Adapter<JointAdapter.JointAdapterViewHolder>(), OnCarDeleteListener {


    private val lstCar = lst;

    private lateinit var onCarSelectedListener: OnCarSelectedListener

    private var selectedId: Int = -1

    private lateinit var progressDialogUtil: ProgressDialogUtil


    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): JointAdapterViewHolder {
        progressDialogUtil = ProgressDialogUtil(parent.context)
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

        viewHolder.imgDelete.setOnClickListener {
            selectedId = lstCar[position].id
            init(lstCar[position].id)
        }

    }

    private fun init(id: Int?) {
        progressDialogUtil.show()
        val deleteCarAsync = DeleteCarAsync()
        deleteCarAsync.setOnCarDeleteListener(this, "?id=$id")
    }

    inner class JointAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val lblName = itemView.lblName
        val imgDelete = itemView.imgDelete


    }

    fun setOnCarSelectedListener(onCarSelectedListener: OnCarSelectedListener) {
        this.onCarSelectedListener = onCarSelectedListener
    }


    override fun onDeleteSuccess(result: String?) {
        progressDialogUtil.hide()
        if (result.isNullOrEmpty() && result.equals("1", true)) {
            lstCar.drop(selectedId)
            notifyDataSetChanged()
        }

    }

}