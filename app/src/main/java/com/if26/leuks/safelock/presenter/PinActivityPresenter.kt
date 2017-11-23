package com.if26.leuks.safelock.presenter

import android.os.AsyncTask
import com.if26.leuks.safelock.PinActivity
import com.if26.leuks.safelock.task.CheckUserTask

/**
 * Created by leuks on 23/11/2017.
 */
class PinActivityPresenter(private var _activity : PinActivity) {
    fun checkUSerExistence(){
        CheckUserTask(null, _activity, CheckUserTask.CHECK_USER_CREATION).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun checkUserPassword(pin : String){
        CheckUserTask(pin, _activity, CheckUserTask.CHECK_USER_PASSWORD).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}