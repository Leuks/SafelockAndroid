package com.if26.leuks.safelock.db.entitie;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by leuks on 16/11/2017.
 */

@DatabaseTable(tableName = "WebSite")
public class WebSite {

    /**
     * @var name
     */
    @DatabaseField(id = true)
    private String name;

    /**
     * @var image path
     */
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] image;

    /**
     * @var website
     * OneToMany
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Link link;

    public WebSite() {
    }

    public WebSite(String name) {
        this.name = name;
        //api to get image
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
