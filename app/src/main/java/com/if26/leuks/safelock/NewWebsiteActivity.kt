package com.if26.leuks.safelock

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import com.if26.leuks.safelock.presenter.NewWebsiteActivityPresenter
import kotlinx.android.synthetic.main.activity_new_website.*

/**
 * Created by leuks on 21/11/2017.
 */
class NewWebsiteActivity : AppCompatActivity() {
    private lateinit var _presenter : NewWebsiteActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_website)

        _presenter = NewWebsiteActivityPresenter(this)

        val base = "https://www."

        tv_url.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                val text = tv_url.text.trim().toString()
                val splitted = text.split(".")
                val website = splitted.get(1)
                _presenter.getLogoAsync(website, iv_website_logo)
            }
        }

        var canChange = true
        tv_url.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(canChange) {


                }
                else{
                    tv_url.setText(base)
                    tv_url.setSelection(tv_url.length())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                canChange = true
                if(s.toString().equals(base) && (start < base.length)){
                    canChange = false
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        button_go.setOnClickListener {
            val login = tv_login.text.trim().toString()
            val url = tv_url.text.trim().toString()
            val bitmap = (iv_website_logo.drawable as BitmapDrawable).bitmap
            val passwd = tv_password.text.trim().toString()

            if (login.isNotEmpty()) {
                if (Patterns.WEB_URL.matcher(url).matches()) {
                    if(passwd.isNotEmpty()){
                        _presenter.addEntry(url, login, passwd, bitmap)
                    }
                    else{
                        tv_password.setError(getString(R.string.invalid_password))
                    }
                } else {
                    tv_url.setError(getString(R.string.invalid_url))
                }
            } else {
                tv_login.setError(getString(R.string.empty_login))
            }


        }

    }
}