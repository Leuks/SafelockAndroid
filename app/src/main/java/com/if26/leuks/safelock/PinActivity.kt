package com.if26.leuks.safelock

import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import com.if26.leuks.safelock.R
import com.if26.leuks.safelock.presenter.PinActivityPresenter
import com.if26.leuks.safelock.task.CheckUserTask
import com.if26.leuks.safelock.tool.Tools
import kotlinx.android.synthetic.main.activity_pin.*
import java.util.regex.Pattern

/**
 * Created by leuks on 23/11/2017.
 */
class PinActivity : AppCompatActivity() {
    private lateinit var _presenter : PinActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _presenter = PinActivityPresenter(this)

        setContentView(R.layout.activity_pin)

        //buttons action
        b_0.setOnClickListener {
            tv_pin.append("0")
        }

        b_1.setOnClickListener {
            tv_pin.append("1")
        }

        b_2.setOnClickListener {
            tv_pin.append("2")
        }

        b_3.setOnClickListener {
            tv_pin.append("3")
        }

        b_4.setOnClickListener {
            tv_pin.append("4")
        }

        b_5.setOnClickListener {
            tv_pin.append("5")
        }

        b_6.setOnClickListener {
            tv_pin.append("6")
        }

        b_7.setOnClickListener {
            tv_pin.append("7")
        }

        b_8.setOnClickListener {
            tv_pin.append("8")
        }

        b_9.setOnClickListener {
            tv_pin.append("9")
        }

        button_validate.setOnClickListener {
            validatePin()
        }

        button_erase.setOnClickListener {
            val pin = tv_pin.text
            if(pin.length != 0)
                tv_pin.setText(pin.subSequence(0, pin.length - 1))
        }

        button_erase.setOnLongClickListener {
            tv_pin.text.clear()
            true
        }

        _presenter.checkUSerExistence()
    }

    fun validatePin(){
        val pin = tv_pin.text.trim().toString()

        if(Pattern.compile("[0-9]+").matcher(pin).matches()){
            _presenter.checkUserPassword(pin)
        }
    }

    fun dispatchError(){
        Tools.showSnackbar(findViewById(R.id.pin_view), getString(R.string.invalid_password));
    }

    fun showHelloDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.hello)
                .setTitle(R.string.welcome)
                .setPositiveButton("Ok", {dialog, which ->  dialog.dismiss()})
        builder.create().show()
    }
}