package com.if26.leuks.safelock.db.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

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
     * @var password
     * Encrypted password for user/website
     */
    @DatabaseField
    private String password;

    @ForeignCollectionField
    private ForeignCollection<User> users;

    @ForeignCollectionField
    private ForeignCollection<WebSite> webSites;

    public Link() {
    }

    public Link(String password) {
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ForeignCollection<User> getUsers() {
        return users;
    }

    public void setUsers(ForeignCollection<User> users) {
        this.users = users;
    }

    public ForeignCollection<WebSite> getWebSites() {
        return webSites;
    }

    public void setWebSites(ForeignCollection<WebSite> webSites) {
        this.webSites = webSites;
    }
}
