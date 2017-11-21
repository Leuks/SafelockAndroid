package com.if26.leuks.safelock.task;

import android.os.AsyncTask;

import com.if26.leuks.safelock.ListActivity;
import com.if26.leuks.safelock.db.DbManager;
import com.if26.leuks.safelock.db.entitie.Link;
import com.if26.leuks.safelock.ListActivity.ListAdapter.ViewHolder;
import com.if26.leuks.safelock.db.entitie.WebSite;

import java.sql.SQLException;

/**
 * Created by leuks on 21/11/2017.
 */

public class BindWebSiteTask extends AsyncTask<Void, Void, WebSite> {
    private  ViewHolder _holder;
    private Link _link;

    public BindWebSiteTask(ViewHolder holder, Link link){
        _holder = holder;
        _link = link;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected WebSite doInBackground(Void... voids) {
        DbManager manager = DbManager.getInstance();

        WebSite website = null;
        try {
            website = (WebSite) manager.getDaoWebSite().queryForEq("link", _link);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return website;
    }

    @Override
    protected void onPostExecute(WebSite webSite) {
        super.onPostExecute(webSite);
        _holder.getTvLogin().setText(_link.getLogin());
        _holder.getTvWebSite().setText(webSite.getName());
        //_holder.getIvWebsiteLogo().setImageBitmap(webSite.getImage());
    }
}
