package com.if26.leuks.safelock.presenter

import android.os.AsyncTask
import com.if26.leuks.safelock.NewAccountActivity
import com.if26.leuks.safelock.R
import com.if26.leuks.safelock.task.UserCheckTask

/**
 * Created by leuks on 21/11/2017.
 */
class NewAccountActivityPresenter(private var _activity: NewAccountActivity) {

    fun checkUser(login: String, passwd: String) {
        val task = UserCheckTask(_activity, _activity.findViewById(R.id.view_new), UserCheckTask.ACTION_CHECK_LOGIN, login, passwd)
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}