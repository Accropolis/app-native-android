package fr.accropolis.teamdev.accropolis.tools.smartimage;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class WebImage implements SmartImage {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 10000;

    private static WebImageCache webImageCache;

    private String uri;

    public WebImage(String uri) {
        this.uri = uri;
    }

    public Bitmap getBitmap(Context context) {
        // Don't leak context
        if(webImageCache == null) {
            webImageCache = new WebImageCache(context);
        }

        // Try getting bitmap from cache first
        Bitmap bitmap = null;
        if(uri != null) {
            bitmap = webImageCache.get(uri);
            if(bitmap == null) {
                bitmap = getBitmapFromUri(uri);
                if(bitmap != null){
                    webImageCache.put(uri, bitmap);
                }
            }
        }

        return bitmap;
    }

    private Bitmap getBitmapFromUri(String uri) {
        Bitmap bitmap = null;

        try {
            URLConnection conn = new URL(uri).openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            bitmap = BitmapFactory.decodeStream((InputStream) conn.getContent());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static void removeFromCache(String uri) {
        if(webImageCache != null) {
            webImageCache.remove(uri);
        }
    }
}
