package com.if26.leuks.safelock.db.entitie;

import android.graphics.Bitmap;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by leuks on 16/11/2017.
 */

@DatabaseTable(tableName = "WebSite")
public class WebSite implements Serializable {

    /**
     * @var url
     */
    @DatabaseField(id = true)
    private String url;

    /**
     * @var bitmap path
     */
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] bitmap;

    public WebSite() {
    }

    public WebSite(String name, Bitmap bitmap) {
        this.url = name;

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

    public byte[] getBitmap() {
        return bitmap;
    }
}
