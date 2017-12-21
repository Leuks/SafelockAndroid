package com.if26.leuks.safelock.db.entitie;

import com.if26.leuks.safelock.tool.Const;
import com.if26.leuks.safelock.tool.CryptoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.security.GeneralSecurityException;

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
        try {
            this.password = CryptoManager.Companion.encrypt(Const.Companion.getPASSWORD(), password);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        try {
            return  CryptoManager.Companion.decrypt(Const.Companion.getPASSWORD(), password);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
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
