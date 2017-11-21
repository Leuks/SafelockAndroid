package com.if26.leuks.safelock.presenter

import android.os.AsyncTask
import com.if26.leuks.safelock.ListActivity.ListAdapter.ViewHolder
import com.if26.leuks.safelock.ListActivity
import com.if26.leuks.safelock.db.entitie.Link
import com.if26.leuks.safelock.db.entitie.User
import com.if26.leuks.safelock.task.BindWebSiteTask
import com.if26.leuks.safelock.task.GetWebSiteTask

/**
 * Created by leuks on 21/11/2017.
 */
class ListActivityPresenter(private var _activity : ListActivity) {

    fun getAllWebSites(user : User, adapter : ListActivity.ListAdapter){
        GetWebSiteTask(user, adapter, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun bindHolderWithWebSite(holder : ViewHolder, link : Link){
        BindWebSiteTask(holder, link).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun getActivity() : ListActivity{
        return _activity
    }
}