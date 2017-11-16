package com.if26.leuks.safelock.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;

import com.if26.leuks.safelock.ActivityManager;
import com.if26.leuks.safelock.R;
import com.if26.leuks.safelock.db.DbManager;
import com.if26.leuks.safelock.db.entities.User;
import com.if26.leuks.safelock.tools.Tools;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by leuks on 16/11/2017.
 */

public class UserCheckTask extends AsyncTask<Object, Void, Boolean> {
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
    protected Boolean doInBackground(Object... args) {
        switch (_action) {
            case 1:
                try {
                    List<User> userlist = _manager.getDaoUser().queryForEq("login", _login);
                    for (User u : userlist) {
                        if (u.getPassword().equals(_password)) {
                            return true;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 0:
                try {
                    List<User> userlist = _manager.getDaoUser().queryForEq("login", _login);
                    return !(userlist.size() > 0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean value) {
        super.onPostExecute(value);
        _dialog.dismiss();

        switch (_action) {
            case 1:
                if (value) {
                    //GOTO PassWord list view
                } else {
                    Tools.Companion.showSnackbar(_view, _activity.getString(R.string.user_wrong));
                }
                break;
            case 0:
                if (value) {
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
