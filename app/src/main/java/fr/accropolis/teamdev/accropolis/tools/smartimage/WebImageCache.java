package fr.accropolis.teamdev.accropolis.tools.smartimage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class WebImageCache {
    private static final String DISK_CACHE_PATH = "/web_image_cache/";

    private ConcurrentHashMap<String, SoftReference<Bitmap>> memoryCache;
    private String diskCachePath;
    private boolean diskCacheEnabled = false;
    private ExecutorService writeThread;

    public WebImageCache(Context context) {
        // Set up in-memory cache store
        memoryCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>();

        // Set up disk cache store
        Context appContext = context.getApplicationContext();
        diskCachePath = appContext.getCacheDir().getAbsolutePath() + DISK_CACHE_PATH;

        File outFile = new File(diskCachePath);
        outFile.mkdirs();

        diskCacheEnabled = outFile.exists();

        // Set up threadpool for image fetching tasks
        writeThread = Executors.newSingleThreadExecutor();
    }

    public Bitmap get(final String uri) {
        Bitmap bitmap = null;

        // Check for image in memory
        bitmap = getBitmapFromMemory(uri);

        // Check for image on disk cache
        if(bitmap == null) {
            bitmap = getBitmapFromDisk(uri);

            // Write bitmap back into memory cache
            if(bitmap != null) {
                cacheBitmapToMemory(uri, bitmap);
            }
        }

        return bitmap;
    }

    public void put(String uri, Bitmap bitmap) {
        cacheBitmapToMemory(uri, bitmap);
        cacheBitmapToDisk(uri, bitmap);
    }

    public void remove(String uri) {
        if(uri == null){
            return;
        }

        // Remove from memory cache
        memoryCache.remove(getCacheKey(uri));

        // Remove from file cache
        File f = new File(diskCachePath, getCacheKey(uri));
        if(f.exists() && f.isFile()) {
            f.delete();
        }
    }

    public void clear() {
        // Remove everything from memory cache
        memoryCache.clear();

        // Remove everything from file cache
        File cachedFileDir = new File(diskCachePath);
        if(cachedFileDir.exists() && cachedFileDir.isDirectory()) {
            File[] cachedFiles = cachedFileDir.listFiles();
            for(File f : cachedFiles) {
                if(f.exists() && f.isFile()) {
                    f.delete();
                }
            }
        }
    }

    private void cacheBitmapToMemory(final String uri, final Bitmap bitmap) {
        memoryCache.put(getCacheKey(uri), new SoftReference<Bitmap>(bitmap));
    }

    private void cacheBitmapToDisk(final String uri, final Bitmap bitmap) {
        writeThread.execute(new Runnable() {
            @Override
            public void run() {
                if(diskCacheEnabled) {
                    BufferedOutputStream ostream = null;
                    try {
                        ostream = new BufferedOutputStream(new FileOutputStream(new File(diskCachePath, getCacheKey(uri))), 2*1024);
                        bitmap.compress(CompressFormat.PNG, 100, ostream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if(ostream != null) {
                                ostream.flush();
                                ostream.close();
                            }
                        } catch (IOException e) {}
                    }
                }
            }
        });
    }

    private Bitmap getBitmapFromMemory(String uri) {
        Bitmap bitmap = null;
        SoftReference<Bitmap> softRef = memoryCache.get(getCacheKey(uri));
        if(softRef != null){
            bitmap = softRef.get();
        }

        return bitmap;
    }

    private Bitmap getBitmapFromDisk(String uri) {
        Bitmap bitmap = null;
        if(diskCacheEnabled){
            String filePath = getFilePath(uri);
            File file = new File(filePath);
            if(file.exists()) {
                bitmap = BitmapFactory.decodeFile(filePath);
            }
        }
        return bitmap;
    }

    private String getFilePath(String uri) {
        return diskCachePath + getCacheKey(uri);
    }

    private String getCacheKey(String uri) {
        if(uri == null){
            throw new RuntimeException("Null uri passed in");
        } else {
            return uri.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
        }
    }
}
