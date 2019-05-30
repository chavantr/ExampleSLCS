package com.mywings.smartcarlock

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.mywings.smartcarlock.joint.JointAdapter
import com.mywings.smartcarlock.listener.OnCarSelectedListener
import com.mywings.smartcarlock.model.Car
import com.mywings.smartcarlock.model.UserInfoHolder
import com.mywings.smartcarlock.process.GetCarAsync
import com.mywings.smartcarlock.process.OnGetCarListener
import com.mywings.smartcarlock.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnGetCarListener,
    OnCarSelectedListener {


    private lateinit var progressDialogUtil: ProgressDialogUtil
    private lateinit var jointAdapter: JointAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)

        progressDialogUtil = ProgressDialogUtil(this)

        lstCar.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            val intent = Intent(this@DashboardActivity, AddCarActivity::class.java)
            startActivity(intent)
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        init()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {

            }
            R.id.nav_gallery -> {

            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun init() {
        progressDialogUtil.show()
        val getCarAsync = GetCarAsync()
        getCarAsync.setOnGetCarListener(this, UserInfoHolder.getInstance().user.id.toString())
    }

    override fun onCarSuccess(result: List<Car>?) {
        progressDialogUtil.hide()
        if (null != result) {
            jointAdapter = JointAdapter(result)
            jointAdapter.setOnCarSelectedListener(this)
            lstCar.adapter = jointAdapter
        }

    }

    override fun onCarSelectedSuccess(car: Car) {
        UserInfoHolder.getInstance().selectedCar = car
        val intent = Intent(this@DashboardActivity, CarDetailsActivity::class.java)
        startActivity(intent)
    }
}
