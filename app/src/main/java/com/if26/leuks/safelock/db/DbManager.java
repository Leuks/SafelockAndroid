package com.if26.leuks.safelock.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.if26.leuks.safelock.db.entitie.Link;
import com.if26.leuks.safelock.db.entitie.User;
import com.if26.leuks.safelock.db.entitie.WebSite;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by leuks on 16/11/2017.
 */

public class DbManager extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "base2";
    private static final int DATABASE_VERSION = 1;

    private static DbManager _instance;

    private Dao<User, Integer> daoUser = null;
    private Dao<Link, Integer> daoLink = null;
    private Dao<WebSite, String> daoWebSite = null;

    private DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, Link.class);
            TableUtils.createTableIfNotExists(connectionSource, WebSite.class);

            Log.i(DbManager.class.getName(), "DBManager onCreate");
        } catch (SQLException e) {
            Log.e(DbManager.class.getName(), "DBManager can't create database", e);
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
    }


    private void init() { //String path
        getDaoUser();
        getDaoLink();
        getDaoWebSite();
    }

    public static DbManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new DbManager(context);
            _instance.init();
        }
        return _instance;
    }

    public static DbManager getInstance() {
        if (_instance == null) {
            throw new IllegalStateException("DbManager isn't initialized");
        }
        return _instance;
    }

    protected void releaseInstance() {
        if (_instance != null) {
            OpenHelperManager.releaseHelper();
            _instance = null;
        }
    }

    public Dao<User, Integer> getDaoUser() {
        if (daoUser == null) {
            try {
                daoUser = DaoManager.createDao(connectionSource, User.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return daoUser;
    }

    public Dao<Link, Integer> getDaoLink() {
        if (daoLink == null) {
            try {
                daoLink = DaoManager.createDao(connectionSource, Link.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return daoLink;
    }

    public Dao<WebSite, String> getDaoWebSite() {
        if (daoWebSite == null) {
            try {
                daoWebSite = DaoManager.createDao(connectionSource, WebSite.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return daoWebSite;
    }

}
