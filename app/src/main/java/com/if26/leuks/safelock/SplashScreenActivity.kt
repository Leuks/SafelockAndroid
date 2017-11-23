package com.if26.leuks.safelock

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.if26.leuks.safelock.db.DbManager
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import com.if26.leuks.safelock.tool.Const


/**
 * Created by leuks on 16/11/2017.
 */
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Init database
        DbManager.getInstance(this)

        ActivityManager.pin(this)
    }
}