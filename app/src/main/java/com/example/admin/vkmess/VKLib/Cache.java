package com.example.admin.vkmess.VKLib;

import android.graphics.Bitmap;
import android.util.LruCache;

public class Cache {
    private static LruCache<Integer, Bitmap> mMemoryCache;


    public static void setCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(Integer key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };

    }

    static void addBitmapToMemoryCache(Integer key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    static Bitmap getBitmapFromMemCache(Integer key) {
        return mMemoryCache.get(key);
    }
}
