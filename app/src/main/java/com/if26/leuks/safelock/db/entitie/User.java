package com.if26.leuks.safelock.db.entitie;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by leuks on 16/11/2017.
 */


@DatabaseTable(tableName = "User")
public class User implements Serializable {

    /**
     * @var id
     */
    @DatabaseField(generatedId = true)
    int id;

    /**
     * @var login
     */
    @DatabaseField
    private String login;

    /**
     * @var password
     */
    @DatabaseField
    private String password;

    /**
     * @var links
     * ManyToOne
     */
    @ForeignCollectionField
    private ForeignCollection<Link> links;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public ForeignCollection<Link> getLinks() {
        return links;
    }

    public void setLinks(ForeignCollection<Link> links) {
        this.links = links;
    }
}
