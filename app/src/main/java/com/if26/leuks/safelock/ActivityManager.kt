package com.if26.leuks.safelock

import android.app.Activity
import android.content.Intent
import com.if26.leuks.safelock.db.entitie.Link
import com.if26.leuks.safelock.db.entitie.User
import com.if26.leuks.safelock.db.entitie.WebSite

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

        fun list(activity: Activity, user: User) {
            val intent = Intent(activity, ListActivity::class.java)
            intent.putExtra("user", user);
            activity.startActivity(intent)
        }

        fun new_website(activity: Activity, user: User, websites: ArrayList<WebSite>, link : Link?) {
            val intent = Intent(activity, NewWebsiteActivity::class.java)
            intent.putExtra("user", user)
            intent.putExtra("websites", websites)
            intent.putExtra("link", link)
            activity.startActivity(intent)
        }
    }
}