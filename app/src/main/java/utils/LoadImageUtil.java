package utils;

import android.widget.ImageView;

/**
 * Created by Ryx on 2019/2/20.
 */
public class LoadImageUtil {
    private static final String GG_BASEURL = "http://47.92.254.172";
    public static void displayImageResize(ImageView imageView, String url) {
        GlideApp.with(MyApplicationContext.getInstance()).load(resolveUrl(url)).override(imageView.getMeasuredWidth()).into(imageView);
    }

    public static void displayImageResize(ImageView imageView, String url, int size) {
        GlideApp.with(MyApplicationContext.getInstance()).load(resolveUrl(url)).override(size).into(imageView);
    }
    public static void loadImage(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideApp.with(MyApplicationContext.getInstance()).load(GG_BASEURL+url).centerCrop().into(imageView);
    }
    public static String resolveUrl(String url) {
        if (url == null)
            return "";
        if (url.startsWith("http:")) {
            return url;
        } else {
            return "http://47.97.211.84" + (url.startsWith("/") ? url : "/" + url);
        }
    }
}
