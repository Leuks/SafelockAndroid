package com.if26.leuks.safelock.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.if26.leuks.safelock.ListActivity;
import com.if26.leuks.safelock.db.DbManager;
import com.if26.leuks.safelock.db.entitie.Link;
import com.if26.leuks.safelock.db.entitie.User;
import com.if26.leuks.safelock.presenter.ListActivityPresenter;
import com.j256.ormlite.dao.ForeignCollection;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by leuks on 21/11/2017.
 */
public class GetWebSiteTask extends AsyncTask<Void, ForeignCollection<Link>, ForeignCollection<Link>> {
    private User _user ;
    private ProgressDialog _dialog;
    private ListActivity.ListAdapter _adapter;

    public GetWebSiteTask(User user, ListActivity.ListAdapter adapter, ListActivityPresenter presenter){
        _user = user;
        _dialog = new ProgressDialog(presenter.getActivity());
        _adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        _dialog.show();
    }

    @Override
    protected ForeignCollection<Link> doInBackground(Void... voids) {
        DbManager manager = DbManager.getInstance();
        ForeignCollection<Link> dbUserLinkList = null;

        try {
            User dbUser = manager.getDaoUser().queryForId(_user.getId());
            dbUserLinkList = dbUser.getLinks();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch(NullPointerException e1){
            e1.printStackTrace();
        }

        return dbUserLinkList;
    }

    @Override
    protected void onPostExecute(ForeignCollection<Link> dbUserLinkList) {
        super.onPostExecute(dbUserLinkList);
        _dialog.dismiss();
        _adapter.setDatas(dbUserLinkList);
    }
}

