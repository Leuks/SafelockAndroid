package com.if26.leuks.safelock

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.if26.leuks.safelock.db.DbManager

/**
 * Created by leuks on 16/11/2017.
 */
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //init database
        DbManager.getInstance(this)

        ActivityManager.login(this, null)
    }
}