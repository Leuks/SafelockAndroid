package com.if26.leuks.safelock.db.entitie;

import android.graphics.Bitmap;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.ByteArrayOutputStream;

/**
 * Created by leuks on 16/11/2017.
 */

@DatabaseTable(tableName = "WebSite")
public class WebSite {

    /**
     * @var url
     */
    @DatabaseField(id = true)
    private String url;

    /**
     * @var url
     */
    @DatabaseField
    private String login;

    /**
     * @var url
     */
    @DatabaseField
    private String password;

    /**
     * @var bitmap path
     */
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] bitmap;

    public WebSite() {
    }

    public WebSite(String name, String login, String password, Bitmap bitmap) {
        this.url = name;
        this.login = login;
        this.password  = password;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        this.bitmap = stream.toByteArray();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }
}
