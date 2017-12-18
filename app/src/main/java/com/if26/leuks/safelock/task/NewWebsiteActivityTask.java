package com.if26.leuks.safelock.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.if26.leuks.safelock.ActivityManager;
import com.if26.leuks.safelock.db.DbManager;
import com.if26.leuks.safelock.db.entitie.User;
import com.if26.leuks.safelock.db.entitie.WebSite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leuks on 14/12/2017.
 */

public class NewWebsiteActivityTask extends AsyncTask<Void, ArrayList<WebSite>, ArrayList<WebSite>> {
    private ProgressDialog _dialog;
    private User _user;
    private Activity _activity;

    public NewWebsiteActivityTask(Activity activity, User user) {
        _dialog = new ProgressDialog(activity);
        _user = user;
        _activity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        _dialog.show();
    }

    @Override
    protected ArrayList<WebSite> doInBackground(Void... voids) {
        DbManager manager = DbManager.getInstance();
        ArrayList<WebSite> websites = new ArrayList<>();

        try {
            List<WebSite> webSitesFromDb = manager.getDaoWebSite().queryForAll();
            websites.addAll(webSitesFromDb);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return websites;
    }

    @Override
    protected void onPostExecute(ArrayList<WebSite> websites) {
        super.onPostExecute(websites);
        _dialog.dismiss();
        ActivityManager.Companion.new_website(_activity, _user, websites);
    }
}