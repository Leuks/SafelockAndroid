package com.if26.leuks.safelock.presenter

import android.graphics.Bitmap
import android.util.Patterns
import android.widget.ImageView
import com.if26.leuks.safelock.ActivityManager
import com.if26.leuks.safelock.ListActivity
import com.if26.leuks.safelock.NewWebsiteActivity
import com.if26.leuks.safelock.R
import com.if26.leuks.safelock.db.DbManager
import com.if26.leuks.safelock.db.entitie.Link
import com.if26.leuks.safelock.db.entitie.User
import com.if26.leuks.safelock.db.entitie.WebSite
import com.if26.leuks.safelock.tool.Tools
import kotlinx.android.synthetic.main.activity_new_website.*

/**
 * Created by leuks on 21/11/2017.
 */
class NewWebsiteActivityPresenter(private var _activity : NewWebsiteActivity) {

    fun getLogoAsync(website : String, imageView : ImageView){
        Tools.bindLogoFromWeb(website, imageView)
    }

    fun addEntry(url : String, user : User, login : String, password : String, bitmap : Bitmap){
        Tools.startThread(Runnable {
            val manager = DbManager.getInstance()

            val website = WebSite(url, bitmap)
            val link = Link(login, password, user)

            manager.daoLink.createIfNotExists(link)
            website.link = link
            manager.daoWebSite.createIfNotExists(website)

        })

        _activity.onBackPressed()
    }
}