package utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideApp.with(MyApplicationContext.getInstance())
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}