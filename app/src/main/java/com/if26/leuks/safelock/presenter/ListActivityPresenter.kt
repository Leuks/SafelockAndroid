package com.if26.leuks.safelock.presenter

import android.os.AsyncTask
import com.if26.leuks.safelock.ListActivity.ListAdapter.ViewHolder
import com.if26.leuks.safelock.ListActivity
import com.if26.leuks.safelock.task.GetWebSitesTask

/**
 * Created by leuks on 21/11/2017.
 */
class ListActivityPresenter(private var _activity : ListActivity) {

    fun getAllWebSites(adapter : ListActivity.ListAdapter){
        GetWebSitesTask(adapter, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun getActivity() : ListActivity{
        return _activity
    }
}