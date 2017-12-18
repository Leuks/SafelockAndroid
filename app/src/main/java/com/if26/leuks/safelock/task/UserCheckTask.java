package com.if26.leuks.safelock.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;

import com.if26.leuks.safelock.ActivityManager;
import com.if26.leuks.safelock.R;
import com.if26.leuks.safelock.db.DbManager;
import com.if26.leuks.safelock.db.entitie.User;
import com.if26.leuks.safelock.tool.Tools;
import com.if26.leuks.safelock.tool.Tuple;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by leuks on 16/11/2017.
 */

public class UserCheckTask extends AsyncTask<Object, Void, Tuple<Boolean, User>> {
    public static final int ACTION_CHECK_LOGIN = 0;
    public static final int ACTION_CHECK_USER = 1;

    private ProgressDialog _dialog;
    private DbManager _manager;
    private String _login;
    private String _password;
    private int _action;
    private View _view;
    private Activity _activity;

    public UserCheckTask(Activity activity, View view, int action, String login, String password) {
        _dialog = new ProgressDialog(activity);
        _manager = DbManager.getInstance();
        _action = action;
        _login = login;
        _password = password;
        _view = view;
        _activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        _dialog.show();
    }

    @Override
    protected Tuple<Boolean, User> doInBackground(Object... args) {
        switch (_action) {
            case ACTION_CHECK_USER:
                try {
                    List<User> userlist = _manager.getDaoUser().queryForEq("login", _login);
                    for (User u : userlist) {
                        if (u.getPassword().equals(_password)) {
                            return new Tuple<>(true, u);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case ACTION_CHECK_LOGIN:
                try {
                    List<User> userlist = _manager.getDaoUser().queryForEq("login", _login);
                    return new Tuple<>(!(userlist.size() > 0), null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

        }
        return new Tuple(false, null);
    }

    @Override
    protected void onPostExecute(Tuple<Boolean, User> tuple) {
        super.onPostExecute(tuple);
        _dialog.dismiss();


        switch (_action) {
            case ACTION_CHECK_USER:
                if (tuple.getArg1()) {
                    final User user = tuple.getArg2();
                    ActivityManager.Companion.list(_activity, user);
                } else {
                    Tools.Companion.showSnackbar(_view, _activity.getString(R.string.user_wrong));
                }
                break;
            case ACTION_CHECK_LOGIN:
                if (tuple.getArg1()) {
                    final User user = new User(_login, _password);
                    Tools.Companion.startThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                _manager.getDaoUser().createIfNotExists(user);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    ActivityManager.Companion.login(_activity, user);
                } else {
                    Tools.Companion.showSnackbar(_view, _activity.getString(R.string.user_already_exists));
                }
                break;
        }
    }
}
