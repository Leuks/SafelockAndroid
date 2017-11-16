package com.if26.leuks.safelock

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.if26.leuks.safelock.db.entities.User
import com.if26.leuks.safelock.tasks.UserCheckTask
import com.if26.leuks.safelock.tools.Tools
import kotlinx.android.synthetic.main.activity_login.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val extras = intent.extras
        if (extras != null) {
            var user = extras.get("user")
            if (user != null) {
                user = user as User
                ed_login.setText(user.login)
                ed_password.setText(user.password)
                Tools.showSnackbar(findViewById(R.id.view_login), getString(R.string.user_created))
            }
        }

        tv_no_account.setOnClickListener {
            ActivityManager.newUser(this)
        }

        button_go.setOnClickListener {
            val login = ed_login.text.trim().toString()
            val passwd = ed_password.text.trim().toString()

            val task = UserCheckTask(this, findViewById(R.id.view_login), UserCheckTask.ACTION_CHECK_USER, login, passwd)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
    }
}
