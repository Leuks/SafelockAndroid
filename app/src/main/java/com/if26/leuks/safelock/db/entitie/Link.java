package com.if26.leuks.safelock.db.entitie;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by leuks on 16/11/2017.
 */

@DatabaseTable(tableName = "Link")
public class Link {

    /**
     * @var id
     */
    @DatabaseField(generatedId = true)
    int id;

    /**
     * @var login
     * User login for this website
     */
    @DatabaseField
    private String login;

    /**
     * @var password
     * Encrypted password for user/website
     */
    @DatabaseField
    private String password;

    /**
     * @var user
     * OneToMany
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;

    /**
     * @var links
     * ManyToOne
     */
    @ForeignCollectionField
    private ForeignCollection<WebSite> websites;

    public Link() {
    }

    public Link(String login, String password, User user) {
        this.login = login;
        this.password = password;
        this.user = user;
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

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ForeignCollection<WebSite> getWebsites() {
        return websites;
    }

    public void setWebsites(ForeignCollection<WebSite> websites) {
        this.websites = websites;
    }
}
