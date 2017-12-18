package com.if26.leuks.safelock

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import com.if26.leuks.safelock.db.entitie.User
import com.if26.leuks.safelock.presenter.NewWebsiteActivityPresenter
import kotlinx.android.synthetic.main.activity_new_website.*
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.*
import com.if26.leuks.safelock.db.entitie.WebSite


/**
 * Created by leuks on 21/11/2017.
 */
class NewWebsiteActivity : AppCompatActivity() {
    private lateinit var _presenter : NewWebsiteActivityPresenter
    private lateinit var _user : User
    private lateinit var _websites : ArrayList<WebSite>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_website)

        _user = intent.extras.get("user") as User
        _websites = intent.extras.get("websites") as ArrayList<WebSite>
        _presenter = NewWebsiteActivityPresenter(this)

        tv_login.setText(_user.login)

        val base = "https://www."
        var canChange = true
        tv_url.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(!canChange) {
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

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        tv_url.threshold = 0

        tv_url.setOnFocusChangeListener( { view, hasFocus ->
            if (!hasFocus) {
                val text = tv_url.text.toString().trim()
                val splitted = text.split(".")
                val website = splitted.get(1)
                _presenter.getLogoAsync(website, iv_website_logo)
            }
        })

        tv_url.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, _websites.cut()))

        button_go.setOnClickListener {
            val login = tv_login.text.trim().toString()
            val url = tv_url.text.trim().toString()
            val bitmap = (iv_website_logo.drawable as BitmapDrawable).bitmap
            val passwd = tv_password.text.trim().toString()

            if (login.isNotEmpty()) {
                if (Patterns.WEB_URL.matcher(url).matches()) {
                    if(passwd.isNotEmpty()){
                        _presenter.addEntry(url, _user, login, passwd, bitmap)
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

    fun ArrayList<WebSite>.cut() : ArrayList<String>{
        val list = ArrayList<String>()
        this.forEach {
            list.add(it.url)
        }
        return list
    }
}