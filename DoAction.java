package uz.android.muhammadjon_tohirov.turbo_test_boss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.FormatException;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

/**
 * Created by M_hacker on 06.12.2016.
 */
public class DoAction {
    private static String TAG = "DO ACTION";

    public static String convertIntToDate(Integer intDate, String format) throws FormatException {
        return new SimpleDateFormat(format).format(intDate * 1000L);
    }

    public static String convertBitmapToString(Bitmap bitmap) {
        Bitmap bm1 = bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String result = Base64.encodeToString(b, Base64.DEFAULT);
        return result;
    }

    public static Bitmap convertByteArrayToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static InputStream convertBitmapToInputStream(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
        Log.d(TAG, "convertBitmapToInputStream: done");
        return bis;
    }

    public static Bitmap convertStringToBitmap(String base64) {
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        return convertByteArrayToBitmap(bytes);
    }

    public static boolean loadImage(String url, Bitmap b) {

            AsyncTask<String, Void, Bitmap> as = new AsyncTask<String, Void, Bitmap>(){
                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                }

                @Override
                protected Bitmap doInBackground(String... strings) {
                    try {
                        URLConnection conn = null;
                        URL u = new URL(strings[0]);
                        conn = u.openConnection();
                        conn.connect();
                        InputStream istream = conn.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(istream);
                        bis.close();
                        istream.close();
                        Log.e("trying", "for url " + strings[0]);
                        return BitmapFactory.decodeStream(bis);
                    } catch (Exception e) {
                        Log.e("failed", "for url "+strings[0]);
                        return null;
                    }
                }
            };
        as.execute(url);
        return true;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}
