package com.if26.leuks.safelock

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.if26.leuks.safelock.tasks.UserCheckTask
import kotlinx.android.synthetic.main.activity_new_account.*

/**
 * Created by leuks on 16/11/2017.
 */
class NewAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_account)

        tv_have_account.setOnClickListener {
            ActivityManager.login(this, null)
        }

        button_new.setOnClickListener {
            val login = ed_login.text.trim().toString()
            val passwd = ed_password.text.trim().toString()

            val task = UserCheckTask(this, findViewById(R.id.view_new), UserCheckTask.ACTION_CHECK_LOGIN, login, passwd)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
    }


}