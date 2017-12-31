package com.if26.leuks.safelock

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
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
import android.widget.TextView
import com.if26.leuks.safelock.db.entitie.Link
import com.if26.leuks.safelock.db.entitie.User
import com.if26.leuks.safelock.presenter.ListActivityPresenter
import com.j256.ormlite.dao.ForeignCollection
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.app_bar_list.*
import kotlinx.android.synthetic.main.content_list.*
import android.app.SearchManager
import android.content.Context
import android.support.v7.widget.SearchView
import android.text.Html
import android.widget.Filter
import android.widget.Filterable
import kotlinx.android.synthetic.main.nav_header_list.view.*


class ListActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var _presenter: ListActivityPresenter
    private lateinit var _user: User
    private lateinit var _goodDatas: ArrayList<Link>

    init{
        _goodDatas = ArrayList<Link>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        _user = intent.extras.get("user") as User
        _presenter = ListActivityPresenter(this)

        initRecycler()

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        search.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        search.setSubmitButtonEnabled(true)
        search.setOnQueryTextListener(this)

        fab.setOnClickListener { view ->
            _presenter.workForNewWebsiteActivity(_user);
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return onQueryTextChange(query)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val adapter = recycler_list.adapter as ListAdapter
        if (newText != null) {
            if(newText.isEmpty())
                adapter.resetDatas()
            else
                adapter.filter.filter(newText)
        }
        return true
    }

    inner class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>, Filterable {
        var _datas: ArrayList<Link>
        lateinit var _lastLink: Link

        constructor() {
            _datas = ArrayList()
        }

        override fun getFilter(): Filter {
            var filter = object : Filter(){
                override fun performFiltering(p0: CharSequence?): FilterResults {
                    val p1 = FilterResults()
                    val filtered = ArrayList<Link>();
                    var const = p0.toString().trim().toLowerCase()
                    _goodDatas.forEach {
                        var isGood = true
                        for (i in 0..const.length-1){
                            if(!it.website.url.toString().trim().toLowerCase().subSequence(12,it.website.url.length).contains(const.get(i))) {
                                isGood = false
                                break
                            }
                        }
                        if(isGood)
                            filtered.add(it)
                    }
                    p1.count = filtered.size
                    p1.values = filtered

                    return p1
                }

                override fun publishResults(constraint: CharSequence?, p1: FilterResults?) {
                    setFileteredDatas(p1?.values as ArrayList<Link>)
                }
            }
            return filter
        }

        fun setDatas(datas: ForeignCollection<Link>) {
            _datas.clear()
            _goodDatas.clear()
            _datas.addAll(datas)
            _goodDatas.addAll(datas)
            notifyDataSetChanged()
        }

        fun  setFileteredDatas(datas: ArrayList<Link>){
            _datas.clear()
            _datas.addAll(datas)
            notifyDataSetChanged()
        }

        fun resetDatas(){
            _datas.clear()
            _datas.addAll(_goodDatas)
            notifyDataSetChanged()
        }

        fun remove(position: Int) {
            _lastLink = _datas.removeAt(position)
            notifyDataSetChanged()
            askForUnRemove(_lastLink)
        }

        fun repair() {
            if (_lastLink != null) {
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

        fun getItem(position: Int): Link {
            return _datas.get(position)
        }

        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            var tvWebSite: TextView = v.findViewById(R.id.tv_website)
            var tvLogin: TextView = v.findViewById(R.id.tv_login)
            var ivWebsiteLogo: ImageView = v.findViewById(R.id.iv_website_logo)
            lateinit var link: Link

            fun bindData(link: Link) {
                var url = link.website.url;
                var newUrl = url.substring(0, 12)
                val query = search.query.trim()
                if(!query.isEmpty()) {
                    for (i in 12..url.length - 5){
                        var letter = url.get(i)
                        if(query.contains(letter)){
                            newUrl += "<font color='#E65100'>" + letter + "</font>"
                        }
                        else
                            newUrl += letter
                    }
                    newUrl += url.substring(url.length-4, url.length)

                    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                        tvWebSite.setText(Html.fromHtml(newUrl, Html.FROM_HTML_MODE_LEGACY))
                    else
                        tvWebSite.setText(Html.fromHtml(newUrl))
                }
                else
                    tvWebSite.setText(url)

                tvLogin.setText(link.user.login)
                ivWebsiteLogo.setImageBitmap(BitmapFactory.decodeByteArray(link.website.getBitmap(), 0, link.website.getBitmap().size));
                this.link = link
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (recycler_list.adapter != null) {
            _presenter.getAllWebSites(_user, recycler_list.adapter as ListAdapter)
            search.setQuery("", false)
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
                if (direction == ItemTouchHelper.LEFT) {
                    (recycler_list.adapter as ListAdapter).remove(position)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recycler_list)
    }


    fun showInfoDialog(link: Link) {
        val dialog = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        dialog.setTitle(getString(R.string.tv_login_password))
        dialog.setMessage(link.password)

        dialog.setPositiveButton("Ok", { dialogInterface, i ->
            dialogInterface.dismiss()
        })

        dialog.setNegativeButton("EDIT", { dialogInterface, i ->
            _presenter.workForNewWebsiteActivity(_user, link)
        })

        dialog.create().show()
    }

    fun askForUnRemove(link: Link) {
        var bar = Snackbar.make(recycler_list, getString(R.string.sure_message), Snackbar.LENGTH_LONG)
        var remove = true
        bar.setAction("UNDO", {
            remove = false
            (recycler_list.adapter as ListAdapter).repair()
        })

        bar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                if (remove) {
                    _presenter.removeLink(link)
                }
            }
        })

        bar.show()
    }
}

