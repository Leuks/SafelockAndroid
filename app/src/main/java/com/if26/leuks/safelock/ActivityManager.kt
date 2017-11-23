package com.if26.leuks.safelock

import android.app.Activity
import android.content.Intent

/**
 * Created by leuks on 16/11/2017.
 */
class ActivityManager {
    companion object {
        fun list(activity: Activity) {
            val intent = Intent(activity, ListActivity::class.java)
            activity.startActivity(intent)
        }

        fun new_website(activity: Activity){
            val intent = Intent(activity, NewWebsiteActivity::class.java)
            activity.startActivity(intent)
        }

        fun pin(activity: Activity){
            val intent = Intent(activity, PinActivity::class.java)
            activity.startActivity(intent)
        }
    }
}