package com.if26.leuks.safelock.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by leuks on 21/11/2017.
 */

public class GetLogoTask extends AsyncTask<Void, Bitmap, Bitmap> {
    private String _url;
    private ImageView _imageView;

    public GetLogoTask(String url, ImageView imageView){
        _url = url;
        _imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
}

    @Override
    protected Bitmap doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(_url).build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            if(response != null)
                response.close();
            e.printStackTrace();
        }

        InputStream inputStream = response.body().byteStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        try {
            if(inputStream != null)
                inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap != null)
            _imageView.setImageBitmap(bitmap);
    }
}
