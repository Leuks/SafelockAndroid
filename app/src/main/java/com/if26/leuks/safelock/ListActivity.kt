package com.if26.leuks.safelock

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.if26.leuks.safelock.db.DbManager
import com.if26.leuks.safelock.db.entitie.Link
import com.if26.leuks.safelock.db.entitie.User
import com.if26.leuks.safelock.db.entitie.WebSite
import com.if26.leuks.safelock.presenter.ListActivityPresenter
import com.if26.leuks.safelock.task.NewWebsiteActivityTask
import com.if26.leuks.safelock.tool.Tools
import com.j256.ormlite.dao.ForeignCollection
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.app_bar_list.*
import kotlinx.android.synthetic.main.content_list.*


class ListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var _presenter : ListActivityPresenter
    private lateinit var _user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        _user = intent.extras.get("user") as User
        _presenter = ListActivityPresenter(this)

        initRecycler()

        fab.setOnClickListener { view ->
            _presenter.workForNewWebsiteActivity(_user);
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
        var _datas : ArrayList<Link>
        lateinit var _lastLink : Link

        constructor(){
            _datas = ArrayList()
        }

        fun setDatas(datas : ForeignCollection<Link>){
            _datas.clear()
            _datas.addAll(datas)
            notifyDataSetChanged()
        }

        fun remove(position : Int){
            _lastLink = _datas.removeAt(position)
            notifyDataSetChanged()
            askForUnRemove(_lastLink)
        }

        fun repair(){
            if(_lastLink != null){
                _datas.add(_lastLink)
            }
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            val v = LayoutInflater.from(parent?.getContext())
                    .inflate(R.layout.recycler_item, parent, false)

            val holder = ViewHolder(v)

            v.setOnClickListener({
                showInfoDialog(holder.link)
            })

            return holder
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            (holder as ViewHolder).bindData(_datas.get(position))
        }

        override fun getItemCount(): Int {
            return _datas.size
        }

        fun getItem(position : Int) : Link{
            return _datas.get(position)
        }

        inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
            var tvWebSite : TextView = v.findViewById(R.id.tv_website)
            var tvLogin : TextView = v.findViewById(R.id.tv_login)
            var ivWebsiteLogo : ImageView= v.findViewById(R.id.iv_website_logo)
            lateinit var link : Link

            fun bindData(link : Link){
                tvWebSite.setText(link.website.url)
                tvLogin.setText(link.user.login)
                ivWebsiteLogo.setImageBitmap(BitmapFactory.decodeByteArray(link.website.getBitmap(), 0, link.website.getBitmap().size));
                this.link = link
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (recycler_list.adapter != null){
            _presenter.getAllWebSites(_user, recycler_list.adapter as ListAdapter)
        }
    }

    fun initRecycler() {
        recycler_list.layoutManager = LinearLayoutManager(this)
        recycler_list.hasFixedSize()
        recycler_list.adapter = ListAdapter()
        recycler_list.addItemDecoration(DividerItemDecoration(recycler_list.context, LinearLayoutManager.VERTICAL))

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if(direction == ItemTouchHelper.LEFT) {
                    (recycler_list.adapter as ListAdapter).remove(position)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recycler_list)
    }


    fun showInfoDialog(link : Link){
        val dialog = AlertDialog.Builder(this,  R.style.AlertDialogTheme)
        dialog.setTitle(getString(R.string.tv_login_password))
        dialog.setMessage(link.password)

        dialog.setPositiveButton("Ok", {
            dialogInterface, i ->
            dialogInterface.dismiss()
        })

        dialog.create().show()
    }

    fun askForUnRemove(link : Link){
        var bar = Snackbar.make(recycler_list, getString(R.string.sure_message), Snackbar.LENGTH_LONG)
        var remove = true
        bar.setAction("UNDO", {
            remove = false
            (recycler_list.adapter as ListAdapter).repair()
        })

        bar.addCallback(object : Snackbar.Callback(){
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                if(remove) {
                    _presenter.removeLink(link)
                }
            }
        })

        bar.show()
    }
}

