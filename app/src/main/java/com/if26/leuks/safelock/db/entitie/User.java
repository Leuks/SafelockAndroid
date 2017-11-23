package com.if26.leuks.safelock.db.entitie;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by leuks on 23/11/2017.
 */

@DatabaseTable(tableName = "User")
public class User {

    /**
     * @var id
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * @var password
     */
    @DatabaseField
    private String password;

    public User(){}

    public User(String password){
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
