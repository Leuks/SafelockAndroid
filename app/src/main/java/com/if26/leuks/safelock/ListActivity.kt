package com.if26.leuks.safelock

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.if26.leuks.safelock.db.entitie.WebSite
import com.if26.leuks.safelock.presenter.ListActivityPresenter
import com.j256.ormlite.dao.ForeignCollection
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.app_bar_list.*
import kotlinx.android.synthetic.main.content_list.*


class ListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var _presenter : ListActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        _presenter = ListActivityPresenter(this)

        recycler_list.layoutManager = LinearLayoutManager(this)
        recycler_list.hasFixedSize()
        recycler_list.adapter = ListAdapter()

        fab.setOnClickListener { view ->
            ActivityManager.new_website(this)
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    inner class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{
        var _datas : ArrayList<WebSite>

        constructor(){
            _datas = ArrayList()
            _presenter.getAllWebSites(this)
        }

        fun setDatas(datas : ArrayList<WebSite>){
            _datas.addAll(datas)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            val v = LayoutInflater.from(parent?.getContext())
                    .inflate(R.layout.recycler_item, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            (holder as ViewHolder).bindDatas(_datas.get(position))
        }

        override fun getItemCount(): Int {
            return _datas.size
        }

        inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
            var tvWebSite : TextView = v.findViewById(R.id.tv_website)
            var tvLogin : TextView = v.findViewById(R.id.tv_login)
            var ivWebsiteLogo : ImageView= v.findViewById(R.id.iv_website_logo)

            fun bindDatas(webSite: WebSite){
                tvWebSite.setText(webSite.url)
                tvLogin.setText(webSite.login)
                ivWebsiteLogo.setImageBitmap(BitmapFactory.decodeByteArray(webSite.getBitmap(), 0, webSite.getBitmap().size));
            }
        }
    }

    fun getRecycler(): RecyclerView{
      return recycler_list;
    }
}
