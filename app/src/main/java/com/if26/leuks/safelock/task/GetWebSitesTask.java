package com.if26.leuks.safelock.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.if26.leuks.safelock.ListActivity;
import com.if26.leuks.safelock.db.DbManager;
import com.if26.leuks.safelock.db.entitie.WebSite;
import com.if26.leuks.safelock.presenter.ListActivityPresenter;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by leuks on 21/11/2017.
 */
public class GetWebSitesTask extends AsyncTask<Void, ArrayList<WebSite>, ArrayList<WebSite>> {
    private ProgressDialog _dialog;
    private ListActivity.ListAdapter _adapter;

    public GetWebSitesTask(ListActivity.ListAdapter adapter, ListActivityPresenter presenter){
        _dialog = new ProgressDialog(presenter.getActivity());
        _adapter = adapter;
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
            websites.addAll(manager.getDaoWebSite().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch(NullPointerException e1){
            e1.printStackTrace();
        }

        return websites;
    }

    @Override
    protected void onPostExecute(ArrayList<WebSite> webSites) {
        super.onPostExecute(webSites);
        _dialog.dismiss();
        _adapter.setDatas(webSites);
    }
}

