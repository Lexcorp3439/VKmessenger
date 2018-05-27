package com.example.admin.vkmess.VKLib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import static com.example.admin.vkmess.VKLib.Cache.addBitmapToMemoryCache;
import static com.example.admin.vkmess.VKLib.Cache.getBitmapFromMemCache;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    private ImageView bmImage;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private Integer id;

    public DownloadImage(ImageView bmImage, Context context, Integer id) {
        this.bmImage = bmImage;
        this.context = context;
        this.id = id;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap bitmap = getBitmapFromMemCache(id);
        if (bitmap != null) {
            return bitmap;
        } else {
            String urlDisplay = urls[0];
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
                addBitmapToMemoryCache(id, bitmap);
            } catch (Exception e) {
                Log.e("Ошибка передачи изображения", e.getMessage());
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        RoundedBitmapDrawable rb = RoundedBitmapDrawableFactory.create(context.getResources(), result);
        rb.setCircular(true);
        bmImage.setImageDrawable(rb);
    }
}