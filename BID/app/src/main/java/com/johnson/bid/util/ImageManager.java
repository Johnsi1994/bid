package com.johnson.bid.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.johnson.bid.R;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.concurrent.Executors;

public class ImageManager {

    private LruCache mLruCache;
    private LruCache mLruCacheBrief;

    private static class ImageManagerHolder {
        private static final ImageManager INSTANCE = new ImageManager();
    }

    private ImageManager() {
        initLruCache();
        initLruCacheBrief();
    }

    public static final ImageManager getInstance() {
        return ImageManagerHolder.INSTANCE;
    }

    private void initLruCache() {

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 2;

        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    private void initLruCacheBrief() {

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 4;

        mLruCacheBrief = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public LruCache getLruCache() {
        return mLruCache;
    }

    public LruCache getLruCacheBrief() {
        return mLruCacheBrief;
    }

    public void setImageByUrl(ImageView imageView, String imageUrl) {

        if (imageUrl != null) {
            Bitmap bitmap = (Bitmap) getLruCache().get(imageUrl);

            if (bitmap == null) {

                Log.d(Constants.TAG, "LruCache doesn't exist, start download.: " + imageUrl);

                lockImagePairing(imageView, imageUrl);
                imageView.setImageResource(R.drawable.ic_128);

                new DownloadImageTask(imageView, imageUrl)
                        .executeOnExecutor(Executors.newCachedThreadPool());
            } else {

                Log.d(Constants.TAG, "LruCache exist, setImageByUrl bitmap directly.: " + imageUrl);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public void setBriefImageByUrl(ImageView imageView, String imageUrl) {

        if (imageUrl != null) {
            Bitmap bitmap = (Bitmap) getLruCacheBrief().get(imageUrl);

            if (bitmap == null) {

                Log.d(Constants.TAG, "LruCacheBrief doesn't exist, start download.: " + imageUrl);

                lockImagePairing(imageView, imageUrl);
                imageView.setImageResource(R.drawable.ic_128);

                new DownloadBriefImageTask(imageView, imageUrl)
                        .executeOnExecutor(Executors.newCachedThreadPool());
            } else {

                Log.d(Constants.TAG, "LruCacheBrief exist, setImageByUrl bitmap directly.: " + imageUrl);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap decodeBitmap(String url, int maxWidth) {

        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxWidth);

            InputStream is = (InputStream) new URL(url).getContent();
            bitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (MalformedInputException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public Bitmap decodeBriefBitmap(String url) {

        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 4;

            InputStream is = (InputStream) new URL(url).getContent();
            bitmap = BitmapFactory.decodeStream(is, null, options);

        } catch (MalformedInputException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private class DownloadImageTask extends AsyncTask {

        private ImageView mImageView;
        private String mImageUrl;
        private Bitmap mBitmap;

        public DownloadImageTask(ImageView imageView, String imageUrl) {

            mImageView = imageView;
            mImageUrl = imageUrl;

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            mBitmap = decodeBitmap(mImageUrl, 200);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (mBitmap != null) {

                getLruCache().put(mImageUrl, mBitmap);

                if (isImagePairingCorrect(mImageView, mImageUrl)) {
                    mImageView.setImageBitmap(mBitmap);
                }
            }
        }
    }

    private class DownloadBriefImageTask extends AsyncTask {

        private ImageView mImageView;
        private String mImageUrl;
        private Bitmap mBitmap;

        public DownloadBriefImageTask(ImageView imageView, String imageUrl) {

            mImageView = imageView;
            mImageUrl = imageUrl;

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            mBitmap = decodeBriefBitmap(mImageUrl);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (mBitmap != null) {

                getLruCacheBrief().put(mImageUrl, mBitmap);

                if (isImagePairingCorrect(mImageView, mImageUrl)) {
                    mImageView.setImageBitmap(mBitmap);
                }
            }
        }
    }

    private void lockImagePairing(ImageView imageView, String url) {
        imageView.setTag(url);
    }

    private boolean isImagePairingCorrect(ImageView imageView, String url) {
        return imageView.getTag().equals(url);
    }

}
