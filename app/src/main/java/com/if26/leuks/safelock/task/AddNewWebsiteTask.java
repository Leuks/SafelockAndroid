package com.if26.leuks.safelock.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.if26.leuks.safelock.db.DbManager;
import com.if26.leuks.safelock.db.entitie.WebSite;

import java.sql.SQLException;

/**
 * Created by leuks on 23/11/2017.
 */

public class AddNewWebsiteTask extends AsyncTask<Void, Void, Void> {
    private WebSite _webSite;
    private Activity _activity;
    private ProgressDialog _dialog;

    public AddNewWebsiteTask(WebSite webSite, Activity activity){
        _webSite = webSite;
        _dialog = new ProgressDialog(activity);
        _activity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        _dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DbManager manager = DbManager.getInstance();

        try {
            manager.getDaoWebSite().createIfNotExists(_webSite);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        _dialog.dismiss();
        _activity.onBackPressed();
    }
}
