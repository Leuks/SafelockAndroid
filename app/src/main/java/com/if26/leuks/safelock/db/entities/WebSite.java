package com.if26.leuks.safelock.db.entities;

import com.j256.ormlite.field.DatabaseField;
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
    @DatabaseField
    private String image;

    /**
     * @var link
     * Link for website
     */
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Link link;

    public WebSite() {
    }

    public WebSite(String name, String image) {
        this.name = name;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
