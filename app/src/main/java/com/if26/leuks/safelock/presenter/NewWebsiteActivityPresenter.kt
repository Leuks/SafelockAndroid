package com.if26.leuks.safelock.presenter

import android.graphics.Bitmap
import android.os.AsyncTask
import android.widget.ImageView
import com.if26.leuks.safelock.NewWebsiteActivity
import com.if26.leuks.safelock.db.entitie.User
import com.if26.leuks.safelock.db.entitie.WebSite
import com.if26.leuks.safelock.task.AddNewWebsiteTask
import com.if26.leuks.safelock.tool.Tools

/**
 * Created by leuks on 21/11/2017.
 */
class NewWebsiteActivityPresenter(private var _activity: NewWebsiteActivity) {

    fun getLogoAsync(website: String, imageView: ImageView) {
        Tools.bindLogoFromWeb(website, imageView)
    }

    fun addEntry(url: String, user: User, login: String, password: String, bitmap: Bitmap) {
        val website = WebSite(url, bitmap)
        AddNewWebsiteTask(website, user, login, password, _activity).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}