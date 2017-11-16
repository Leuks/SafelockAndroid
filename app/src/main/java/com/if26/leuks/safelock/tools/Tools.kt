package com.if26.leuks.safelock.tools

import android.support.design.widget.Snackbar
import android.view.View

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
    }
}