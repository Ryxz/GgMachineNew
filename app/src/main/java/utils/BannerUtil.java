package utils;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author Ryx on 2018/12/12
 */
public class BannerUtil {
    private static List<String> mDatas;
    private static final String GG_BASEURL = "http://47.92.254.172";

    public BannerUtil() {
    }

    public static void bannerImgeOne(final Banner banner, String path) {
        mDatas = new ArrayList<>();
                String picData = GG_BASEURL +path;
                L.e("TAG:",picData);
                if (MediaFileUtil.isImageFileType(picData)) {
                    mDatas.add(picData);
                    banner.setBannerStyle(BannerConfig.NOT_INDICATOR)
                            .setImageLoader(new GlideImageLoader())
                            .setImages(mDatas)
                            .setBannerAnimation(Transformer.Default)
                            .isAutoPlay(true)
                            .setDelayTime(2000)
                            .start();
                } else {
                    ToastUtil.showMessage("后台文件添加错误，添加了不该添加的文件类型！");
                }



    }
    public static void bannerImgeTwo(final Banner banner, String path) {
        mDatas = new ArrayList<>();
        String picData = GG_BASEURL + path;
        L.e("TAG:", picData);
        if (MediaFileUtil.isImageFileType(picData)) {
            mDatas.add(picData);

            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                    .setImageLoader(new GlideImageLoader())
                    .setImages(mDatas)
                    .setBannerAnimation(Transformer.Default)
                    .isAutoPlay(true)
                    .setDelayTime(2000)
                    .setIndicatorGravity(BannerConfig.CENTER)
                    .start();
        } else {
            ToastUtil.showMessage("后台文件添加错误，添加了不该添加的文件类型！");
        }

    }

}
