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

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    private ImageView bmImage;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public DownloadImage(ImageView bmImage, Context context) {
        this.bmImage = bmImage;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Ошибка передачи изображения", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {

        RoundedBitmapDrawable rb = RoundedBitmapDrawableFactory.create( context.getResources(),result);
        rb.setCircular(true);
        //bmImage.setImageBitmap(result);
        bmImage.setImageDrawable(rb);
    }
}