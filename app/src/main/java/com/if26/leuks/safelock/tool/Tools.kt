package com.if26.leuks.safelock.tool

import android.support.design.widget.Snackbar
import android.view.View
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.os.AsyncTask
import android.widget.ImageView
import com.if26.leuks.safelock.task.GetLogoTask


/**
 * Created by leuks on 16/11/2017.
 */
class Tools {
    companion object {
        fun startThread(runnable: Runnable) {
            val thread = Thread(runnable)
            thread.start();
        }

        fun showSnackbar(vg: View, content: String) {
            Snackbar.make(vg, content, Snackbar.LENGTH_SHORT).show()
        }

        fun bindLogoFromWeb(wanted : String, imageView : ImageView){
            val url = "https://logo.clearbit.com/$wanted.com"
            GetLogoTask(url, imageView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
    }
}