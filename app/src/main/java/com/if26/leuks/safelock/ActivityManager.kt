package com.if26.leuks.safelock

import android.app.Activity
import android.content.Intent
import com.if26.leuks.safelock.db.entities.User

/**
 * Created by leuks on 16/11/2017.
 */
class ActivityManager {
    companion object {
        fun login(activity: Activity, user: User?) {
            val intent = Intent(activity, LoginActivity::class.java)
            if (user != null) {
                intent.putExtra("user", user);
            }

            activity.startActivity(intent)
            activity.finish()
        }

        fun newUser(activity: Activity) {
            val intent = Intent(activity, NewAccountActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }
}