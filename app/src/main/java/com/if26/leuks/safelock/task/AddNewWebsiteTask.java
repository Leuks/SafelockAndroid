package com.if26.leuks.safelock.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.if26.leuks.safelock.db.DbManager;
import com.if26.leuks.safelock.db.entitie.Link;
import com.if26.leuks.safelock.db.entitie.User;
import com.if26.leuks.safelock.db.entitie.WebSite;

import java.sql.SQLException;

/**
 * Created by leuks on 23/11/2017.
 */

public class AddNewWebsiteTask extends AsyncTask<Void, Void, Void> {
    private WebSite _webSite;
    private Activity _activity;
    private ProgressDialog _dialog;
    private User _user;
    private String _login;
    private String _password;

    public AddNewWebsiteTask(WebSite webSite, User user, String login, String password, Activity activity) {
        _webSite = webSite;
        _dialog = new ProgressDialog(activity);
        _activity = activity;
        _user = user;
        _login = login;
        _password = password;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        _dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DbManager manager = DbManager.getInstance();

        Link link = new Link(_login, _password, _user, _webSite);

        try {
            manager.getDaoWebSite().createIfNotExists(_webSite);
            manager.getDaoLink().createIfNotExists(link);
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
