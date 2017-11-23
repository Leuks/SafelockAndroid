package com.if26.leuks.safelock.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.if26.leuks.safelock.ActivityManager;
import com.if26.leuks.safelock.PinActivity;
import com.if26.leuks.safelock.db.DbManager;
import com.if26.leuks.safelock.db.entitie.User;
import com.if26.leuks.safelock.db.entitie.WebSite;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by leuks on 23/11/2017.
 */

public class CheckUserTask extends AsyncTask<Void, Void, Boolean> {
    private String _password;
    private PinActivity _activity;
    private ProgressDialog _dialog;
    private int _action;

    public static int CHECK_USER_CREATION = 0;
    public static int CHECK_USER_PASSWORD = 1;

    public CheckUserTask(String password, PinActivity activity, int action){
        _password = password;
        _activity = activity;
        _dialog  = new ProgressDialog(activity.getBaseContext());
        _action = action;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //_dialog.create();
        //_dialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        DbManager manager = DbManager.getInstance();

        User user = null;
        try{
            List<User> list = manager.getDaoUser().queryForAll();
            if(list.size() > 0)
                user = list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        switch (_action){
            case 0 :
                if(user == null)
                    return false;
                else
                    return true;
            case 1:
                if(user == null){
                    user = new User(_password);

                    try {
                        manager.getDaoUser().createIfNotExists(user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    return true;
                }
               else {
                    if (user.getPassword().equals(_password))
                        return true;
                    else
                        return false;
                }
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean value) {
        super.onPostExecute(value);
        _dialog.dismiss();

        switch (_action){
            case 0 :
                if(!value){
                    _activity.showHelloDialog();
                }
                break;
            case 1:
                if(value)
                    ActivityManager.Companion.list(_activity);
                else
                    _activity.dispatchError();
                break;
        }
    }
}
