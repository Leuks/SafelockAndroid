package com.if26.leuks.safelock.presenter

import android.os.AsyncTask
import com.if26.leuks.safelock.ListActivity
import com.if26.leuks.safelock.db.DbManager
import com.if26.leuks.safelock.db.entitie.Link
import com.if26.leuks.safelock.db.entitie.User
import com.if26.leuks.safelock.task.GetWebSitesTask
import com.if26.leuks.safelock.task.NewWebsiteActivityTask
import com.if26.leuks.safelock.tool.Tools

/**
 * Created by leuks on 21/11/2017.
 */
class ListActivityPresenter(private var _activity : ListActivity) {

    fun getAllWebSites(user : User, adapter : ListActivity.ListAdapter){
        GetWebSitesTask(user, adapter, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun removeLink(link : Link){
        Tools.startThread(Runnable {
            val manager = DbManager.getInstance()
            manager.daoLink.delete(link)
        })
    }

    fun workForNewWebsiteActivity(user : User) {
        NewWebsiteActivityTask(_activity, user).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun getActivity() : ListActivity{
        return _activity
    }
}